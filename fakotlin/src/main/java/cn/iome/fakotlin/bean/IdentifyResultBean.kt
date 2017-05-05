package cn.iome.fakotlin.bean

class IdentifyResultBean {

    /**
     * log_id : 73473737
     * result_num : 1
     * result : [{"uid":"u333333","user_info":"Test User","scores":[99.3,83.4]}]
     */

    var log_id: Long = 0
    var result_num: Int = 0
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * uid : u333333
         * user_info : Test User
         * scores : [99.3,83.4]
         */

        var uid: String? = null
        var user_info: String? = null
        var scores: List<Double>? = null
        var groups: List<String>? = null
    }

    override fun toString(): String {
        return "IdentifyResultBean{" +
                "log_id=" + log_id +
                ", result_num=" + result_num +
                ", result=" + result +
                '}'
    }
}
