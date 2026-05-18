package com.pop.components.theme

import com.pop.compose_components.R

/**
 * Type-safe icon name interface.
 * Only icons that implement this interface can be used in the icon system.
 * This prevents using arbitrary strings and ensures compile-time type safety.
 *
 * Each icon declares its available resource variants (outline and/or filled).
 * Use `null` for variants that don't exist.
 */
sealed interface IconName {
    val value: String

    /**
     * Outline variant drawable resource ID.
     * Use `null` if this icon doesn't have an outline variant.
     */
    val outlineRes: Int?

    /**
     * Filled variant drawable resource ID.
     * Use `null` if this icon doesn't have a filled variant.
     */
    val filledRes: Int?
}

/**
 * Icon objects namespace.
 * Access icons as: Icons.ArrowDown, Icons.Heart, etc.
 */
object Icons {

    // ========== Share Icons ==========

    object Share05 : IconName {
        override val value = "ic_share_05"
        override val outlineRes: Int? = R.drawable.ic_share_05_outline
        override val filledRes: Int? = R.drawable.ic_share_05_filled
    }

    object Share01 : IconName {
        override val value = "ic_share_01"
        override val outlineRes: Int? = R.drawable.ic_share_01_outline
        override val filledRes: Int? = R.drawable.ic_share_01_outline
    }

    object Copy06 : IconName {
        override val value = "ic_copy_06"
        override val outlineRes: Int? = R.drawable.ic_copy_06
        override val filledRes: Int? = R.drawable.ic_copy_06
    }

    object ChevronDown16 : IconName {
        override val value = "ic_chevron_down_16"
        override val outlineRes: Int? = R.drawable.ic_chevron_down_16
        override val filledRes: Int? = R.drawable.ic_chevron_down_16
    }

    object ChevronDown12 : IconName {
        override val value = "ic_chevron_down_12"
        override val outlineRes: Int? = R.drawable.ic_chevron_down_12
        override val filledRes: Int? = R.drawable.ic_chevron_down_12
    }

    object Share06 : IconName {
        override val value = "ic_share_06"
        override val outlineRes: Int? = R.drawable.ic_share_06_outline
        override val filledRes: Int? = R.drawable.ic_share_06_filled
    }

    object ShareAppBar: IconName{
        override val value = "ic_share_appbar"
        override val outlineRes: Int? = R.drawable.ic_share_appbar
        override val filledRes: Int? = R.drawable.ic_share_appbar
    }
    object ChevronLeft : IconName {
        override val value = "chevron_left"
        override val outlineRes: Int? = R.drawable.back_icon
        override val filledRes: Int? = R.drawable.back_icon
    }

    object ChevronDown : IconName {
        override val value = "chevron_down"
        override val outlineRes: Int? = R.drawable.ic_arrow_down_1
        override val filledRes: Int? = R.drawable.ic_arrow_down_1
    }

    object ChevronRight : IconName {
        override val value = "chevron_right"
        override val outlineRes: Int? = R.drawable.ic_chevron_right_14
        override val filledRes: Int? = R.drawable.ic_chevron_right_14
    }

    object Cross : IconName {
        override val value = "cross"
        override val outlineRes: Int? = R.drawable.cross
        override val filledRes: Int? = R.drawable.cross
    }

    object Check : IconName {
        override val value = "check"
        override val outlineRes: Int? = R.drawable.check
        override val filledRes: Int? = R.drawable.check
    }

    object CheckBoxSelected : IconName {
        override val value = "check"
        override val outlineRes: Int? = R.drawable.check_box_selected
        override val filledRes: Int? = R.drawable.check_box_selected
    }

    object CheckBoxIndeterminate : IconName {
        override val value = "check"
        override val outlineRes: Int? = R.drawable.check_box_indeterminate
        override val filledRes: Int? = R.drawable.check_box_indeterminate
    }

    object Mobile : IconName {
        override val value = "mobile"
        override val outlineRes: Int? = R.drawable.ic_mobile_outline
        override val filledRes: Int? = R.drawable.ic_mobile_outline
    }

    object AlertHexagon : IconName {
        override val value = "alert_hexagon"
        override val outlineRes: Int? = R.drawable.ic_alert_hexagon_outline
        override val filledRes: Int? = R.drawable.ic_alert_hexagon_outline
    }

    object BackSpaceIcon : IconName {
        override val value = "back_space"
        override val outlineRes: Int? = R.drawable.ic_back_space
        override val filledRes: Int? = R.drawable.ic_back_space
    }

    object Search : IconName {
        override val value = "search"
        override val outlineRes: Int? = R.drawable.ic_search_outline
        override val filledRes: Int? = R.drawable.ic_search_outline
    }

    object WhatsApp : IconName {
        override val value = "whatsapp_square"
        override val outlineRes: Int? = R.drawable.ic_whatsapp_square
        override val filledRes: Int? = R.drawable.ic_whatsapp_square
    }

    object Download : IconName {
        override val value = "download"
        override val outlineRes: Int? = R.drawable.ic_download
        override val filledRes: Int? = R.drawable.ic_download
    }

    object CheckIcon : IconName {
        override val value = "check"
        override val outlineRes: Int? = R.drawable.ic_check_filled
        override val filledRes: Int? = R.drawable.ic_check_filled
    }

    object HelpCircle : IconName {
        override val value = "ic_help_circle"
        override val outlineRes: Int? = R.drawable.ic_help_circle
        override val filledRes: Int? = R.drawable.ic_help_circle
    }

    object HomeBottomNav : IconName {
        override val value = "ic_home_bottom_nav"
        override val outlineRes: Int? = R.drawable.ic_home_bottom_nav
        override val filledRes: Int? = R.drawable.ic_home_bottom_nav
    }

    object ShopBottomNav : IconName {
        override val value = "ic_shop_bottom_nav"
        override val outlineRes: Int? = R.drawable.ic_shop_bottom_nav
        override val filledRes: Int? = R.drawable.ic_shop_bottom_nav
    }

    object CardBottomNav : IconName {
        override val value = "ic_card_bottom_nav"
        override val outlineRes: Int? = R.drawable.ic_card_bottom_nav
        override val filledRes: Int? = R.drawable.ic_card_bottom_nav
    }

    object Flag : IconName {
        override val value = "ic_flag"
        override val outlineRes: Int? = R.drawable.ic_flag
        override val filledRes: Int? = R.drawable.ic_flag
    }

    object InfoSquare : IconName {
        override val value = "info_square"
        override val outlineRes: Int? = R.drawable.ic_info_square
        override val filledRes: Int? = R.drawable.ic_info_square
    }

    object AlertCircle : IconName {
        override val value = "alert_circle"
        override val outlineRes: Int? = R.drawable.ic_alert_circle
        override val filledRes: Int? = R.drawable.ic_alert_circle
    }

    object CheckVerified : IconName {
        override val value = "check_verified"
        override val outlineRes: Int? = R.drawable.ic_check_verified
        override val filledRes: Int? = R.drawable.ic_check_verified
    }

    object AlertWarning : IconName {
        override val value = "alert_triangle"
        override val outlineRes: Int? = R.drawable.ic_alert_triangle
        override val filledRes: Int? = R.drawable.ic_alert_triangle
    }

    object RupeeSymbol : IconName {
        override val value = "rupee_symbol"
        override val outlineRes: Int? = R.drawable.ic_rupee_symbol
        override val filledRes: Int? = R.drawable.ic_rupee_symbol
    }

    object User01 : IconName {
        override val value = "user_01"
        override val outlineRes: Int? = R.drawable.ic_user_01
        override val filledRes: Int? = R.drawable.ic_user_01
    }

    object Trash : IconName {
        override val value = "trash"
        override val outlineRes: Int? = R.drawable.ic_trash
        override val filledRes: Int? = R.drawable.ic_trash
    }

    object Download01 : IconName {
        override val value = "ic_download_01"
        override val outlineRes: Int? = R.drawable.ic_download_01
        override val filledRes: Int? = R.drawable.ic_download_01
    }

    object AddIcon : IconName {
        override val value = "add"
        override val outlineRes: Int? = R.drawable.add_icon
        override val filledRes: Int? = R.drawable.add_icon
    }

    object NoResultsFound : IconName {
        override val value = "no_results_found"
        override val outlineRes: Int? = R.drawable.ic_no_results
        override val filledRes: Int? = R.drawable.ic_no_results
    }

    object Contacts : IconName {
        override val value = "contacts"
        override val outlineRes: Int? = R.drawable.img_offfer_star
        override val filledRes: Int? = R.drawable.img_offfer_star
    }

    object Settings : IconName {
        override val value = "add"
        override val outlineRes: Int? = R.drawable.settings_icon
        override val filledRes: Int? = R.drawable.settings_icon
    }

    object VpaVerified : IconName {
        override val value = "vpa_verified"
        override val outlineRes: Int = R.drawable.ic_vpa_verified_rs
        override val filledRes: Int = R.drawable.ic_vpa_verified_rs
    }

    object ViewHistory : IconName {
        override val value = "view_history"
        override val outlineRes: Int = R.drawable.ic_view_history
        override val filledRes: Int = R.drawable.ic_view_history

    }


    object Edit : IconName {
        override val value = "edit"
        override val outlineRes: Int? = R.drawable.ic_edit_pencil_grey
        override val filledRes: Int? = R.drawable.ic_edit_pencil_grey
    }

    object QR : IconName {
        override val value = "qrCode"
        override val outlineRes: Int? = R.drawable.ic_qr_grey_curved
        override val filledRes: Int? = R.drawable.ic_qr_grey_curved
    }

    object ScanQR : IconName {
        override val value = "scanQrCode"
        override val outlineRes: Int? = R.drawable.scan_qr_fab
        override val filledRes: Int? = R.drawable.scan_qr_fab
    }

    object Reset : IconName {
        override val value = "reset"
        override val outlineRes: Int? = R.drawable.ic_timer_arrow_grey
        override val filledRes: Int? = R.drawable.ic_timer_arrow_grey
    }

    object Reset01 : IconName {
        override val value = "ic_reset"
        override val outlineRes: Int? = R.drawable.ic_reset
        override val filledRes: Int? = R.drawable.ic_reset
    }

    object Bank : IconName {
        override val value = "bank"
        override val outlineRes: Int? = R.drawable.ic_bank
        override val filledRes: Int? = R.drawable.ic_bank
    }

    object Support : IconName {
        override val value = "Help & Support"
        override val outlineRes: Int? = R.drawable.ic_support
        override val filledRes: Int? = R.drawable.ic_support
    }

    object SendMoneyArrow : IconName {
        override val value = "SendMoneyArrow"
        override val outlineRes: Int? = R.drawable.ic_send_arrow_grey
        override val filledRes: Int? = R.drawable.ic_send_arrow_grey
    }

    object BankIcon : IconName {
        override val value = "BankIcon"
        override val outlineRes: Int? = R.drawable.ic_bank_grey
        override val filledRes: Int? = R.drawable.ic_bank_grey
    }


    object RupeeIcon : IconName {
        override val value = "RupeeIcon"
        override val outlineRes: Int? = R.drawable.ic_rupee_symbol
        override val filledRes: Int? = R.drawable.ic_rupee_symbol
    }

    object ReceiptCheck : IconName {
        override val value = "ReceiptCheck"
        override val outlineRes: Int? = R.drawable.ic_receipt_check
        override val filledRes: Int? = R.drawable.ic_receipt_check
    }

    object ArrowLoop : IconName {
        override val value = "ArrowLoop"
        override val outlineRes: Int? = R.drawable.ic_arrow_loop_grey
        override val filledRes: Int? = R.drawable.ic_arrow_loop_grey
    }


    object PendingHourGlass : IconName {
        override val value = "PendingHourGlass"
        override val outlineRes: Int? = R.drawable.ic_pending_hourglass_grey
        override val filledRes: Int? = R.drawable.ic_pending_hourglass_grey
    }

    object GreyPin : IconName {
        override val value = "GreyPin"
        override val outlineRes: Int? = R.drawable.ic_grey_pin
        override val filledRes: Int? = R.drawable.ic_grey_pin
    }

    object GiftWrap : IconName {
        override val value = "GiftWrap"
        override val outlineRes: Int? = R.drawable.ic_gift_wrap
        override val filledRes: Int? = R.drawable.ic_gift_wrap
    }


    object HumanAvatar : IconName {
        override val value = "HumanAvatar"
        override val outlineRes: Int? = R.drawable.img_human_avatar
        override val filledRes: Int? = R.drawable.img_human_avatar
    }

    object GreyCard : IconName {
        override val value = "GreyCard"
        override val outlineRes: Int? = R.drawable.ic_card
        override val filledRes: Int? = R.drawable.ic_card
    }

    object UpiLiteFlash : IconName {
        override val value = "UpiLiteFlash"
        override val outlineRes: Int? = R.drawable.ic_glowing_flash_grey
        override val filledRes: Int? = R.drawable.ic_glowing_flash_grey
    }

    object UpiLiteTss : IconName {
        override val value = "UpiLiteTss"
        override val outlineRes: Int? = R.drawable.ic_upilite_tss_icon
        override val filledRes: Int? = R.drawable.ic_upilite_tss_icon
    }

    object BlockedMessage : IconName {
        override val value = "BlockedMessage"
        override val outlineRes: Int? = R.drawable.ic_blocked_message_grey
        override val filledRes: Int? = R.drawable.ic_blocked_message_grey
    }

    object Message : IconName {
        override val value = "Message"
        override val outlineRes: Int? = R.drawable.icon_message_grey
        override val filledRes: Int? = R.drawable.icon_message_grey
    }


    object QueryClosed : IconName {
        override val value = "query_closed"
        override val outlineRes: Int? = R.drawable.ic_complaint_closed
        override val filledRes: Int? = R.drawable.ic_complaint_closed
    }

    object QueryFailed : IconName {
        override val value = "query_failed"
        override val outlineRes: Int? = R.drawable.ic_complaint_failed
        override val filledRes: Int? = R.drawable.ic_complaint_failed
    }

    object QueryReview : IconName {
        override val value = "query_review"
        override val outlineRes: Int? = R.drawable.ic_complaint_review
        override val filledRes: Int? = R.drawable.ic_complaint_review
    }

    object Refresh : IconName {
        override val value = "refresh"
        override val outlineRes: Int? = R.drawable.ic_refresh
        override val filledRes: Int? = R.drawable.ic_refresh
    }

    object SMS : IconName {
        override val value = "sms"
        override val outlineRes: Int? = R.drawable.ic_sms
        override val filledRes: Int? = R.drawable.ic_sms
    }

    object Notification : IconName {
        override val value = "notification"
        override val outlineRes: Int? = R.drawable.ic_notification
        override val filledRes: Int? = R.drawable.ic_notification
    }

    object DefaultCircle : IconName {
        override val value = "default_circle"
        override val outlineRes: Int? = R.drawable.ic_default_circle
        override val filledRes: Int? = R.drawable.ic_default_circle
    }

    object Link : IconName {
        override val value = "link"
        override val outlineRes: Int? = R.drawable.link
        override val filledRes: Int? = R.drawable.link
    }

    object Cart : IconName {
        override val value = "link"
        override val outlineRes: Int? = R.drawable.cart_outlined
        override val filledRes: Int? = R.drawable.ic_cart_white
    }

    object Heart : IconName {
        override val value = "link"
        override val outlineRes: Int? = R.drawable.icon_heart
        override val filledRes: Int? = R.drawable.icon_heart
    }

    object BharatAssured : IconName {
        override val value = "bharat_assured"
        override val outlineRes: Int? = R.drawable.bharat_assured
        override val filledRes: Int? = R.drawable.bharat_assured
    }

    object Close : IconName {
        override val value = "close"
        override val outlineRes: Int? = R.drawable.ic_close_24dp_white
        override val filledRes: Int? = R.drawable.ic_close_24dp_white
    }

    // ========== Profile Screen Icons ==========

    object TransactionHistory : IconName {
        override val value = "transaction_history"
        override val outlineRes: Int? = R.drawable.ic_transaction_history
        override val filledRes: Int? = R.drawable.ic_transaction_history
    }

    object ProfileCard : IconName {
        override val value = "card"
        override val outlineRes: Int? = R.drawable.ic_card
        override val filledRes: Int? = R.drawable.ic_card
    }

    object EverythingUpi : IconName {
        override val value = "everything_upi"
        override val outlineRes: Int? = R.drawable.ic_everything_upi
        override val filledRes: Int? = R.drawable.ic_everything_upi
    }

    object Logout : IconName {
        override val value = "logout"
        override val outlineRes: Int? = R.drawable.ic_logout
        override val filledRes: Int? = R.drawable.ic_logout
    }

    object MyBills : IconName {
        override val value = "my_bills"
        override val outlineRes: Int? = R.drawable.ic_my_bills
        override val filledRes: Int? = R.drawable.ic_my_bills
    }

    object Orders : IconName {
        override val value = "orders"
        override val outlineRes: Int? = R.drawable.ic_orders
        override val filledRes: Int? = R.drawable.ic_orders
    }

    object RateUs : IconName {
        override val value = "rate_us"
        override val outlineRes: Int? = R.drawable.ic_rate_us
        override val filledRes: Int? = R.drawable.ic_rate_us
    }

    object SavedAddress : IconName {
        override val value = "saved_address"
        override val outlineRes: Int? = R.drawable.ic_saved_address
        override val filledRes: Int? = R.drawable.ic_saved_address
    }

    object TermsAndConditions : IconName {
        override val value = "terms_n_conditions"
        override val outlineRes: Int? = R.drawable.ic_terms_n_conditions
        override val filledRes: Int? = R.drawable.ic_terms_n_conditions
    }

    object ProfileWishlist : IconName {
        override val value = "wishlist"
        override val outlineRes: Int? = R.drawable.ic_wishlist
        override val filledRes: Int? = R.drawable.ic_wishlist
    }

    object PayBills : IconName {
        override val value = "pay_bills"
        override val outlineRes: Int? = R.drawable.ic_pay_bills
        override val filledRes: Int? = R.drawable.ic_pay_bills
    }

    // ========== Everything Upi Screen Icons ==========

    object BlockedVpa : IconName {
        override val value = "blocked_vpa"
        override val outlineRes: Int? = R.drawable.ic_blocked_vpa
        override val filledRes: Int? = R.drawable.ic_blocked_vpa
    }

    object CheckBalance : IconName {
        override val value = "check_balance"
        override val outlineRes: Int? = R.drawable.ic_check_balance
        override val filledRes: Int? = R.drawable.ic_check_balance
    }

    object ManageAutoPay : IconName {
        override val value = "manage_auto_pay"
        override val outlineRes: Int? = R.drawable.ic_manage_auto_pay
        override val filledRes: Int? = R.drawable.ic_manage_auto_pay
    }

    object MyQueries : IconName {
        override val value = "my_queries"
        override val outlineRes: Int? = R.drawable.ic_my_queries
        override val filledRes: Int? = R.drawable.ic_my_queries
    }

    object MyUpiId : IconName {
        override val value = "my_upi_id"
        override val outlineRes: Int? = R.drawable.ic_my_upi_id
        override val filledRes: Int? = R.drawable.ic_my_upi_id
    }

    object PayFriends : IconName {
        override val value = "pay_friends"
        override val outlineRes: Int? = R.drawable.ic_pay_friends
        override val filledRes: Int? = R.drawable.ic_pay_friends
    }

    object PendingRequests : IconName {
        override val value = "pending_requests"
        override val outlineRes: Int? = R.drawable.ic_pending_requests
        override val filledRes: Int? = R.drawable.ic_pending_requests
    }

    object EverythingUpiLite : IconName {
        override val value = "upi_lite"
        override val outlineRes: Int? = R.drawable.ic_upi_lite_new
        override val filledRes: Int? = R.drawable.ic_upi_lite_new
    }

    object UpiNumber : IconName {
        override val value = "upi_number"
        override val outlineRes: Int? = R.drawable.ic_upi_number
        override val filledRes: Int? = R.drawable.ic_upi_number
    }

    object UpiSupport : IconName {
        override val value = "upi_support"
        override val outlineRes: Int? = R.drawable.ic_upi_support
        override val filledRes: Int? = R.drawable.ic_upi_support
    }

    object Star : IconName{
        override val value = "star"
        override val outlineRes: Int? = R.drawable.ic_rating_star
        override val filledRes: Int? = R.drawable.ic_rating_star
    }

    object VkycUnderReview : IconName {
        override val value = "vkyc_under_review"
        override val outlineRes: Int? = R.drawable.vkyc_under_review
        override val filledRes: Int? = R.drawable.vkyc_under_review
    }

    object Block : IconName {
        override val value = "block"
        override val outlineRes: Int? = R.drawable.block_icon
        override val filledRes: Int? = R.drawable.block_icon
    }

    object Spam : IconName {
        override val value = "spam"
        override val outlineRes: Int? = R.drawable.spam_icon
        override val filledRes: Int? = R.drawable.spam_icon
    }

    object Headphone : IconName {
        override val value = "headphone"
        override val outlineRes: Int? = R.drawable.headphone
        override val filledRes: Int? = R.drawable.headphone
    }

    object Bright : IconName {
        override val value = "bright"
        override val outlineRes: Int? = R.drawable.ic_bright
        override val filledRes: Int? = R.drawable.ic_bright
    }

    object UserSquare : IconName {
        override val value = "userSquare"
        override val outlineRes: Int? = R.drawable.ic_user_square
        override val filledRes: Int? = R.drawable.ic_user_square
    }

    object EditTertiary : IconName {
        override val value = "edit"
        override val outlineRes: Int? = R.drawable.ic_edit_tertiary
        override val filledRes: Int? = R.drawable.ic_edit_tertiary
    }
    object CashbackLedgerClaimed : IconName {
        override val value = "cashback_ledger_claimed"
        override val outlineRes: Int? = R.drawable.ic_claimed
        override val filledRes: Int? = R.drawable.ic_claimed
    }

    object CashbackLedgerUPIShop: IconName {
        override val value = "cashback_upi_txn"
        override val outlineRes: Int? = R.drawable.ic_cashback_upi
        override val filledRes: Int? = R.drawable.ic_cashback_upi
    }

    object CashbackLedgerReferralBonus : IconName {
        override val value = "cashback_referral"
        override val outlineRes: Int? = R.drawable.ic_referral_bonus
        override val filledRes: Int? = R.drawable.ic_referral_bonus
    }

    object HELP_CIRCLE : IconName {
        override val value = "help_circle"
        override val outlineRes: Int? = R.drawable.ic_help_circle
        override val filledRes: Int? = R.drawable.ic_help_circle
    }

    object ArrowCredited : IconName {
        override val value = "arrow_credited"
        override val outlineRes: Int? = R.drawable.ic_arrow_credited
        override val filledRes: Int? = R.drawable.ic_arrow_credited
    }

    object ArrowDebited : IconName {
        override val value = "arrow_credited"
        override val outlineRes: Int? = R.drawable.ic_arrow_debited
        override val filledRes: Int? = R.drawable.ic_arrow_debited
    }
}
