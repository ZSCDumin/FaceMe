package cn.iome.fakotlin.bean

class QuickBean {


    /**
     * result_num : 1
     * result : [{"location":{"left":65,"top":106,"width":104,"height":102},"face_probability":1,"rotation_angle":-1,"yaw":0.2535697221756,"pitch":3.9080514907837,"roll":-0.95554226636887}]
     * log_id : 2292285852
     */

    var result_num: Int = 0
    var log_id: Long = 0
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * location : {"left":65,"top":106,"width":104,"height":102}
         * face_probability : 1
         * rotation_angle : -1
         * yaw : 0.2535697221756
         * pitch : 3.9080514907837
         * roll : -0.95554226636887
         */

        var location: LocationBean? = null
        var face_probability: Int = 0
        var rotation_angle: Int = 0
        var yaw: Double = 0.toDouble()
        var pitch: Double = 0.toDouble()
        var roll: Double = 0.toDouble()

        class LocationBean {
            /**
             * left : 65
             * top : 106
             * width : 104
             * height : 102
             */

            var left: Int = 0
            var top: Int = 0
            var width: Int = 0
            var height: Int = 0
        }
    }

    override fun toString(): String {
        return "QuickBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}'
    }
}
