package cn.iome.fakotlin.bean

class RecognizeBean {

    /**
     * result_num : 1
     * result : [{"location":{"left":65,"top":106,"width":104,"height":102},"face_probability":1,"rotation_angle":-1,"yaw":0.2535697221756,"pitch":3.9080514907837,"roll":-0.95554226636887,"age":25.807439804077,"beauty":38.110992431641,"expression":0,"expression_probablity":0.99998998641968,"faceshape":[{"type":"square","probability":0.31530252099037},{"type":"triangle","probability":0.006098295096308},{"type":"oval","probability":0.36280432343483},{"type":"heart","probability":0.033966641873121},{"type":"round","probability":0.28182822465897}],"gender":"male","gender_probability":0.9999258518219,"glasses":1,"glasses_probability":0.99992525577545,"race":"yellow","race_probability":1,"qualities":{"occlusion":{"left_eye":0,"right_eye":0,"nose":0,"mouth":0,"left_cheek":0,"right_cheek":0,"chin":0},"blur":0,"illumination":0,"completeness":0,"type":{"human":0.98386383056641,"cartoon":0.016136165708303}}}]
     * log_id : 2185322071
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
         * age : 25.807439804077
         * beauty : 38.110992431641
         * expression : 0
         * expression_probablity : 0.99998998641968
         * faceshape : [{"type":"square","probability":0.31530252099037},{"type":"triangle","probability":0.006098295096308},{"type":"oval","probability":0.36280432343483},{"type":"heart","probability":0.033966641873121},{"type":"round","probability":0.28182822465897}]
         * gender : male
         * gender_probability : 0.9999258518219
         * glasses : 1
         * glasses_probability : 0.99992525577545
         * race : yellow
         * race_probability : 1
         * qualities : {"occlusion":{"left_eye":0,"right_eye":0,"nose":0,"mouth":0,"left_cheek":0,"right_cheek":0,"chin":0},"blur":0,"illumination":0,"completeness":0,"type":{"human":0.98386383056641,"cartoon":0.016136165708303}}
         */

        var location: LocationBean? = null
        var face_probability: Double = 0.toDouble()
        var rotation_angle: Int = 0
        var yaw: Double = 0.toDouble()
        var pitch: Double = 0.toDouble()
        var roll: Double = 0.toDouble()
        var age: Double = 0.toDouble()
        var beauty: Double = 0.toDouble()
        var expression: Int = 0
        var expression_probablity: Double = 0.toDouble()
        var gender: String? = null
        var gender_probability: Double = 0.toDouble()
        var glasses: Int = 0
        var glasses_probability: Double = 0.toDouble()
        var race: String? = null
        var race_probability: Double = 0.toDouble()
        var qualities: QualitiesBean? = null
        var faceshape: List<FaceshapeBean>? = null

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

        class QualitiesBean {
            /**
             * occlusion : {"left_eye":0,"right_eye":0,"nose":0,"mouth":0,"left_cheek":0,"right_cheek":0,"chin":0}
             * blur : 0
             * illumination : 0
             * completeness : 0
             * type : {"human":0.98386383056641,"cartoon":0.016136165708303}
             */

            var occlusion: OcclusionBean? = null
            var blur: Int = 0
            var illumination: Int = 0
            var completeness: Int = 0
            var type: TypeBean? = null

            class OcclusionBean {
                /**
                 * left_eye : 0
                 * right_eye : 0
                 * nose : 0
                 * mouth : 0
                 * left_cheek : 0
                 * right_cheek : 0
                 * chin : 0
                 */

                var left_eye: Int = 0
                var right_eye: Int = 0
                var nose: Int = 0
                var mouth: Int = 0
                var left_cheek: Int = 0
                var right_cheek: Int = 0
                var chin: Int = 0
            }

            class TypeBean {
                /**
                 * human : 0.98386383056641
                 * cartoon : 0.016136165708303
                 */

                var human: Double = 0.toDouble()
                var cartoon: Double = 0.toDouble()
            }
        }

        class FaceshapeBean {
            /**
             * type : square
             * probability : 0.31530252099037
             */

            var type: String? = null
            var probability: Double = 0.toDouble()
        }
    }

    override fun toString(): String {
        return "RecognizeBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}'
    }
}
