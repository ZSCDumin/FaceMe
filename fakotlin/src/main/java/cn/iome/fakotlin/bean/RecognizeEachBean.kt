package cn.iome.fakotlin.bean

class RecognizeEachBean {

    /**
     * result_num : 3
     * results : [{"index_i":"0","index_j":"1","score":37.412288665771},{"index_i":"0","index_j":"2","score":100},{"index_i":"1","index_j":"2","score":37.412288665771}]
     * log_id : 2430996994
     */

    var result_num: Int = 0
    var log_id: Long = 0
    var results: List<ResultsBean>? = null

    class ResultsBean {
        /**
         * index_i : 0
         * index_j : 1
         * score : 37.412288665771
         */

        var index_i: String? = null
        var index_j: String? = null
        var score: Double = 0.toDouble()
    }

    override fun toString(): String {
        return "RecognizeEachBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", results=" + results +
                '}'
    }
}
