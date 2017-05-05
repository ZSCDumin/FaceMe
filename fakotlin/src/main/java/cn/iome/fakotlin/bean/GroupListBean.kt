package cn.iome.fakotlin.bean

class GroupListBean {

    /**
     * result_num : 1
     * result : ["group1"]
     * log_id : 1280748993
     */

    var result_num: Int = 0
    var log_id: Long = 0
    var result: List<String>? = null

    override fun toString(): String {
        return "GroupListBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}'
    }
}
