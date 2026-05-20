package com.pop.components.animation.serialization

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.AnimationEasing
import com.pop.components.animation.config.MaskRevealAnimationConfig
import com.pop.components.animation.config.NoAnimationConfig
import com.pop.components.animation.config.SlideLeftAnimationConfig
import com.pop.components.animation.config.SlideLeftScaleAnimationConfig
import com.pop.components.animation.config.SlideUpMidAnimationConfig
import com.pop.components.animation.config.SlideUpQuickAnimationConfig
import com.pop.components.animation.config.SplitScreenAnimationConfig
import java.lang.reflect.Type
import java.util.ArrayDeque

/**
 * Manages serialization and deserialization of animation configuration stacks.
 * 
 * This class handles converting animation configuration stacks to/from JSON format,
 * enabling state persistence across process death or for debugging purposes.
 * 
 * **Serialization Format:**
 * - Stacks are converted to a JSON object where keys are owner IDs (screenIds)
 * - Each stack (ArrayDeque) is converted to a JSON array of animation configs
 * - Animation configs are serialized with their type information for polymorphic deserialization
 * 
 * **Supported Config Types:**
 * - SlideLeftAnimationConfig
 * - SlideLeftScaleAnimationConfig
 * - SlideUpQuickAnimationConfig
 * - SlideUpMidAnimationConfig
 * - SplitScreenAnimationConfig
 * - MaskRevealAnimationConfig
 * - NoAnimationConfig
 * 
 * **Thread Safety:**
 * - This class is stateless and thread-safe
 * - Serialization/deserialization operations are safe to call from any thread
 * 
 * @see AnimationManager.serializeNavigationAnimationConfigStackMap
 * @see AnimationManager.deserializeNavigationAnimationConfigStackMap
 */
internal class AnimationConfigSerializer {

    private val gsonWithAdapters: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(AnimationConfig::class.java, AnimationConfigTypeAdapter())
            .registerTypeAdapter(AnimationEasing::class.java, AnimationEasingTypeAdapter())
            .create()
    }

    /**
     * Serializes animation configuration stacks to JSON.
     * 
     * Converts the internal stack structure (ArrayDeque) to a JSON-serializable format (List),
     * then serializes the entire map to a JSON string.
     * 
     * **Format:**
     * ```json
     * {
     *   "screenId1": [config1, config2, ...],
     *   "screenId2": [config3, ...]
     * }
     * ```
     *
     * @param stacks Map of owner IDs (screenIds) to their animation config stacks
     * @return JSON string representation of all stacks, or null if stacks are empty
     */
    fun serialize(stacks: Map<String, ArrayDeque<AnimationConfig>>): String? {
        if (stacks.isEmpty()) {
            return null
        }

        // Convert ArrayDeque to List for serialization
        val serializableMap = stacks.mapValues { (_, deque) ->
            deque.toList()
        }

        val mapType = object : TypeToken<Map<String, List<AnimationConfig>>>() {}.type
        return gsonWithAdapters.toJson(serializableMap, mapType)
    }

    /**
     * Deserializes JSON string to animation configuration stacks.
     * 
     * Parses a JSON string back into the internal stack structure, converting
     * JSON arrays (Lists) back to ArrayDeque instances.
     * 
     * **Error Handling:**
     * - Returns empty map if jsonString is blank
     * - Throws JsonParseException if JSON is malformed or contains unknown config types
     * 
     * **Expected Format:**
     * ```json
     * {
     *   "screenId1": [config1, config2, ...],
     *   "screenId2": [config3, ...]
     * }
     * ```
     *
     * @param jsonString JSON string representation of animation config stacks
     * @return Map of owner IDs (screenIds) to their animation config stacks (ArrayDeque),
     *         or empty map if jsonString is blank
     * @throws JsonParseException if the JSON string is invalid, malformed, or contains
     *                           unknown AnimationConfig types
     */
    fun deserialize(jsonString: String): Map<String, ArrayDeque<AnimationConfig>> {
        if (jsonString.isBlank()) {
            return emptyMap()
        }

        val mapType = object : TypeToken<Map<String, List<AnimationConfig>>>() {}.type
        val deserializedMap = gsonWithAdapters.fromJson<Map<String, List<AnimationConfig>>>(jsonString, mapType)
            ?: return emptyMap()

        // Convert List back to ArrayDeque
        val stacks = mutableMapOf<String, ArrayDeque<AnimationConfig>>()
        deserializedMap.forEach { (ownerId, configList) ->
            val deque = ArrayDeque<AnimationConfig>()
            configList.forEach { config ->
                deque.add(config)
            }
            stacks[ownerId] = deque
        }

        return stacks
    }

    /**
     * Custom TypeAdapter for AnimationConfig to handle polymorphic serialization/deserialization.
     * 
     * Since AnimationConfig is a sealed class with multiple implementations, we need
     * custom serialization to preserve the concrete type information.
     * 
     * **Serialization Format:**
     * ```json
     * {
     *   "type": "SlideLeftAnimationConfig",
     *   "data": { ... actual config data ... }
     * }
     * ```
     * 
     * The "type" field stores the class name, and "data" contains the actual config properties.
     */
    private class AnimationConfigTypeAdapter : JsonSerializer<AnimationConfig>, JsonDeserializer<AnimationConfig> {
        override fun serialize(
            src: AnimationConfig,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            val jsonObject = JsonObject()
            jsonObject.addProperty("type", src::class.java.simpleName)
            jsonObject.add("data", context.serialize(src, src::class.java))
            return jsonObject
        }

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): AnimationConfig {
            val jsonObject = json.asJsonObject
            val typeName = jsonObject.get("type").asString
            val dataElement = jsonObject.get("data")

            return when (typeName) {
                "SlideLeftAnimationConfig" -> context.deserialize(dataElement, SlideLeftAnimationConfig::class.java)
                "SlideLeftScaleAnimationConfig" -> context.deserialize(dataElement, SlideLeftScaleAnimationConfig::class.java)
                "SlideUpMidAnimationConfig" -> context.deserialize(dataElement, SlideUpMidAnimationConfig::class.java)
                "SlideUpQuickAnimationConfig" -> context.deserialize(dataElement, SlideUpQuickAnimationConfig::class.java)
                "SplitScreenAnimationConfig" -> context.deserialize(dataElement, SplitScreenAnimationConfig::class.java)
                "NoAnimationConfig" -> context.deserialize(dataElement, NoAnimationConfig::class.java)
                "MaskRevealAnimationConfig" -> context.deserialize(dataElement, MaskRevealAnimationConfig::class.java)
                else -> throw JsonParseException("Unknown AnimationConfig type: $typeName")
            }
        }
    }

    /**
     * Custom TypeAdapter for AnimationEasing to handle polymorphic serialization/deserialization.
     * 
     * AnimationEasing is a sealed class with multiple implementations (EaseOutCubic, AccelerateDecelerate, CubicBezier),
     * so we need custom serialization to preserve the type and parameters.
     * 
     * **Serialization Format:**
     * - EaseOutCubic: `{"type": "EaseOutCubic"}`
     * - AccelerateDecelerate: `{"type": "AccelerateDecelerate"}`
     * - CubicBezier: `{"type": "CubicBezier", "controlX1": ..., "controlY1": ..., ...}`
     */
    private class AnimationEasingTypeAdapter : JsonSerializer<AnimationEasing>, JsonDeserializer<AnimationEasing> {
        override fun serialize(
            src: AnimationEasing,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            val jsonObject = JsonObject()
            when (src) {
                is AnimationEasing.EaseOutCubic -> {
                    jsonObject.addProperty("type", "EaseOutCubic")
                }
                is AnimationEasing.AccelerateDecelerate -> {
                    jsonObject.addProperty("type", "AccelerateDecelerate")
                }
                is AnimationEasing.CubicBezier -> {
                    jsonObject.addProperty("type", "CubicBezier")
                    jsonObject.addProperty("controlX1", src.controlX1)
                    jsonObject.addProperty("controlY1", src.controlY1)
                    jsonObject.addProperty("controlX2", src.controlX2)
                    jsonObject.addProperty("controlY2", src.controlY2)
                }
            }
            return jsonObject
        }

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): AnimationEasing {
            val jsonObject = json.asJsonObject
            val typeName = jsonObject.get("type").asString

            return when (typeName) {
                "EaseOutCubic" -> AnimationEasing.EaseOutCubic
                "AccelerateDecelerate" -> AnimationEasing.AccelerateDecelerate
                "CubicBezier" -> {
                    val controlX1 = jsonObject.get("controlX1").asFloat
                    val controlY1 = jsonObject.get("controlY1").asFloat
                    val controlX2 = jsonObject.get("controlX2").asFloat
                    val controlY2 = jsonObject.get("controlY2").asFloat
                    AnimationEasing.CubicBezier(controlX1, controlY1, controlX2, controlY2)
                }
                else -> throw JsonParseException("Unknown AnimationEasing type: $typeName")
            }
        }
    }
}
