package cn.iome.fakotlin.bean

class FaceResultBean {

    /**
     * error_code : 216616
     * error_msg : image exist
     * log_id : 2733216045
     */

    var log_id: Long = 0
    var error_msg: String? = null
    var error_code: Int = 0
    /**
     * result_num : 2
     * results : [100,100]
     */

    var result_num: Int = 0
    var results: List<Int>? = null

    override fun toString(): String {
        return "FaceResultBean{" +
                "log_id=" + log_id +
                ", error_msg='" + error_msg + '\'' +
                ", error_code=" + error_code +
                ", result_num=" + result_num +
                ", results=" + results +
                '}'
    }
}
