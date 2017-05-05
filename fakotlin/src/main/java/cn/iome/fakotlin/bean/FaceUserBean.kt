package cn.iome.fakotlin.bean

class FaceUserBean {


    /**
     * result : {"uid":"uid1","user_info":"test_user_info","groups":["group1"]}
     * log_id : 1381367636
     */

    var result: ResultBean? = null
    var log_id: Long = 0

    class ResultBean {
        /**
         * uid : uid1
         * user_info : test_user_info
         * groups : ["group1"]
         */

        var uid: String? = null
        var user_info: String? = null
        var groups: List<String>? = null
    }

    override fun toString(): String {
        return "FaceUserBean{" +
                "result=" + result +
                ", log_id=" + log_id +
                '}'
    }
}
