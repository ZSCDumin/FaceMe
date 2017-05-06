package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.utility.FaceManager;

/**
 * Created by haoping on 17/4/10.
 * 快速执行后返回的数据 {@link FaceManager#quick(String, FaceManager.Callback)}
 */
public class QuickBean {


    /**
     * result_num : 1
     * result : [{"location":{"left":65,"top":106,"width":104,"height":102},"face_probability":1,"rotation_angle":-1,"yaw":0.2535697221756,"pitch":3.9080514907837,"roll":-0.95554226636887}]
     * log_id : 2292285852
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
         */

        private LocationBean location;
        private int face_probability;
        private int rotation_angle;
        private double yaw;
        private double pitch;
        private double roll;

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
    }

    @Override
    public String toString() {
        return "QuickBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}';
    }
}
