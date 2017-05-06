package cn.iome.fakotlin.bean

class GroupUserBean {

    /**
     * result_num : 3
     * result : [{"uid":"dq65454dddd","user_info":"test_user_info"},{"uid":"haoping2","user_info":"test_user_info"},{"uid":"uid1","user_info":"test_user_info"}]
     * log_id : 558300064
     */

    var result_num: Int = 0
    var log_id: Long = 0
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * uid : dq65454dddd
         * user_info : test_user_info
         */

        var uid: String? = null
        var user_info: String? = null
    }

    override fun toString(): String {
        return "GroupUserBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}'
    }
}
