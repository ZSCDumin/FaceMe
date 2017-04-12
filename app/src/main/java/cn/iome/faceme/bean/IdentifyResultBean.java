package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.util.FaceUtil;

/**
 * Created by haoping on 17/4/10.
 * {@link FaceUtil#identifyUser(java.lang.String, java.util.ArrayList, java.util.HashMap, FaceUtil.Callback)}
 * {@link FaceUtil#facesetDeleteUser(java.lang.String, FaceUtil.Callback)}
 */
public class IdentifyResultBean {

    /**
     * log_id : 73473737
     * result_num : 1
     * result : [{"uid":"u333333","user_info":"Test User","scores":[99.3,83.4]}]
     */

    private int log_id;
    private int result_num;
    private List<ResultBean> result;

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * uid : u333333
         * user_info : Test User
         * scores : [99.3,83.4]
         */

        private String uid;
        private String user_info;
        private List<Double> scores;
        private List<String> groups;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_info() {
            return user_info;
        }

        public void setUser_info(String user_info) {
            this.user_info = user_info;
        }

        public List<Double> getScores() {
            return scores;
        }

        public void setScores(List<Double> scores) {
            this.scores = scores;
        }

        public List<String> getGroups() {
            return groups;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }
    }

    @Override
    public String toString() {
        return "IdentifyResultBean{" +
                "log_id=" + log_id +
                ", result_num=" + result_num +
                ", result=" + result +
                '}';
    }
}
