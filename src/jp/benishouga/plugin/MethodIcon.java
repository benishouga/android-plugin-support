package jp.benishouga.plugin;

/**
 * プラグイン連携の種別を表現するためのアイコンを指定するための列挙です。
 *
 * @author benishouga
 */
public enum MethodIcon {
    /** カスタムアイコンを表示します。 */
    Custom,
    /** 通話アイコンを表示します。 */
    Call,
    /** メールアイコンを表示します。 */
    Mail,
    /** SMSアイコンを表示します。 */
    Sms,
    /** 地図アイコンを表示します。 */
    Map,
    /** メモアイコンを表示します。 */
    Memo,
    /** World Wide Webアイコンを表示します。 */
    Www,
    /** 名刺アイコンを表示します。 */
    Organizations,
    /** イベントアイコンを表示します。 */
    Event;
}
