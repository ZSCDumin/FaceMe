package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.util.FaceUtil;

/**
 * Created by haoping on 17/4/10.
 * {@link FaceUtil#faceRecognizeWithBytes(byte[], java.util.HashMap, FaceUtil.Callback)}
 * {@link FaceUtil#faceRecognizeWithPath(java.lang.String, java.util.HashMap, FaceUtil.Callback)}
 */
public class RecognizeBean {

    /**
     * result_num : 1
     * result : [{"location":{"left":65,"top":106,"width":104,"height":102},"face_probability":1,"rotation_angle":-1,"yaw":0.2535697221756,"pitch":3.9080514907837,"roll":-0.95554226636887,"age":25.807439804077,"beauty":38.110992431641,"expression":0,"expression_probablity":0.99998998641968,"faceshape":[{"type":"square","probability":0.31530252099037},{"type":"triangle","probability":0.006098295096308},{"type":"oval","probability":0.36280432343483},{"type":"heart","probability":0.033966641873121},{"type":"round","probability":0.28182822465897}],"gender":"male","gender_probability":0.9999258518219,"glasses":1,"glasses_probability":0.99992525577545,"race":"yellow","race_probability":1,"qualities":{"occlusion":{"left_eye":0,"right_eye":0,"nose":0,"mouth":0,"left_cheek":0,"right_cheek":0,"chin":0},"blur":0,"illumination":0,"completeness":0,"type":{"human":0.98386383056641,"cartoon":0.016136165708303}}}]
     * log_id : 2185322071
     */

    private int result_num;
    private long log_id;
    private List<ResultBean> result;

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
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

        private LocationBean location;
        private int face_probability;
        private int rotation_angle;
        private double yaw;
        private double pitch;
        private double roll;
        private double age;
        private double beauty;
        private int expression;
        private double expression_probablity;
        private String gender;
        private double gender_probability;
        private int glasses;
        private double glasses_probability;
        private String race;
        private int race_probability;
        private QualitiesBean qualities;
        private List<FaceshapeBean> faceshape;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public int getFace_probability() {
            return face_probability;
        }

        public void setFace_probability(int face_probability) {
            this.face_probability = face_probability;
        }

        public int getRotation_angle() {
            return rotation_angle;
        }

        public void setRotation_angle(int rotation_angle) {
            this.rotation_angle = rotation_angle;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }

        public double getAge() {
            return age;
        }

        public void setAge(double age) {
            this.age = age;
        }

        public double getBeauty() {
            return beauty;
        }

        public void setBeauty(double beauty) {
            this.beauty = beauty;
        }

        public int getExpression() {
            return expression;
        }

        public void setExpression(int expression) {
            this.expression = expression;
        }

        public double getExpression_probablity() {
            return expression_probablity;
        }

        public void setExpression_probablity(double expression_probablity) {
            this.expression_probablity = expression_probablity;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getGender_probability() {
            return gender_probability;
        }

        public void setGender_probability(double gender_probability) {
            this.gender_probability = gender_probability;
        }

        public int getGlasses() {
            return glasses;
        }

        public void setGlasses(int glasses) {
            this.glasses = glasses;
        }

        public double getGlasses_probability() {
            return glasses_probability;
        }

        public void setGlasses_probability(double glasses_probability) {
            this.glasses_probability = glasses_probability;
        }

        public String getRace() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public int getRace_probability() {
            return race_probability;
        }

        public void setRace_probability(int race_probability) {
            this.race_probability = race_probability;
        }

        public QualitiesBean getQualities() {
            return qualities;
        }

        public void setQualities(QualitiesBean qualities) {
            this.qualities = qualities;
        }

        public List<FaceshapeBean> getFaceshape() {
            return faceshape;
        }

        public void setFaceshape(List<FaceshapeBean> faceshape) {
            this.faceshape = faceshape;
        }

        public static class LocationBean {
            /**
             * left : 65
             * top : 106
             * width : 104
             * height : 102
             */

            private int left;
            private int top;
            private int width;
            private int height;

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        public static class QualitiesBean {
            /**
             * occlusion : {"left_eye":0,"right_eye":0,"nose":0,"mouth":0,"left_cheek":0,"right_cheek":0,"chin":0}
             * blur : 0
             * illumination : 0
             * completeness : 0
             * type : {"human":0.98386383056641,"cartoon":0.016136165708303}
             */

            private OcclusionBean occlusion;
            private int blur;
            private int illumination;
            private int completeness;
            private TypeBean type;

            public OcclusionBean getOcclusion() {
                return occlusion;
            }

            public void setOcclusion(OcclusionBean occlusion) {
                this.occlusion = occlusion;
            }

            public int getBlur() {
                return blur;
            }

            public void setBlur(int blur) {
                this.blur = blur;
            }

            public int getIllumination() {
                return illumination;
            }

            public void setIllumination(int illumination) {
                this.illumination = illumination;
            }

            public int getCompleteness() {
                return completeness;
            }

            public void setCompleteness(int completeness) {
                this.completeness = completeness;
            }

            public TypeBean getType() {
                return type;
            }

            public void setType(TypeBean type) {
                this.type = type;
            }

            public static class OcclusionBean {
                /**
                 * left_eye : 0
                 * right_eye : 0
                 * nose : 0
                 * mouth : 0
                 * left_cheek : 0
                 * right_cheek : 0
                 * chin : 0
                 */

                private int left_eye;
                private int right_eye;
                private int nose;
                private int mouth;
                private int left_cheek;
                private int right_cheek;
                private int chin;

                public int getLeft_eye() {
                    return left_eye;
                }

                public void setLeft_eye(int left_eye) {
                    this.left_eye = left_eye;
                }

                public int getRight_eye() {
                    return right_eye;
                }

                public void setRight_eye(int right_eye) {
                    this.right_eye = right_eye;
                }

                public int getNose() {
                    return nose;
                }

                public void setNose(int nose) {
                    this.nose = nose;
                }

                public int getMouth() {
                    return mouth;
                }

                public void setMouth(int mouth) {
                    this.mouth = mouth;
                }

                public int getLeft_cheek() {
                    return left_cheek;
                }

                public void setLeft_cheek(int left_cheek) {
                    this.left_cheek = left_cheek;
                }

                public int getRight_cheek() {
                    return right_cheek;
                }

                public void setRight_cheek(int right_cheek) {
                    this.right_cheek = right_cheek;
                }

                public int getChin() {
                    return chin;
                }

                public void setChin(int chin) {
                    this.chin = chin;
                }
            }

            public static class TypeBean {
                /**
                 * human : 0.98386383056641
                 * cartoon : 0.016136165708303
                 */

                private double human;
                private double cartoon;

                public double getHuman() {
                    return human;
                }

                public void setHuman(double human) {
                    this.human = human;
                }

                public double getCartoon() {
                    return cartoon;
                }

                public void setCartoon(double cartoon) {
                    this.cartoon = cartoon;
                }
            }
        }

        public static class FaceshapeBean {
            /**
             * type : square
             * probability : 0.31530252099037
             */

            private String type;
            private double probability;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public double getProbability() {
                return probability;
            }

            public void setProbability(double probability) {
                this.probability = probability;
            }
        }
    }

    @Override
    public String toString() {
        return "RecognizeBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}';
    }
}
