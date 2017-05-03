package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.utility.FaceManager;

/**
 * Created by haoping on 17/4/10.
 * {@link FaceManager#getUser(java.lang.String, FaceManager.Callback)}
 */
public class FaceUserBean {


    /**
     * result : {"uid":"uid1","user_info":"test_user_info","groups":["group1"]}
     * log_id : 1381367636
     */

    private ResultBean result;
    private long log_id;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public static class ResultBean {
        /**
         * uid : uid1
         * user_info : test_user_info
         * groups : ["group1"]
         */

        private String uid;
        private String user_info;
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

        public List<String> getGroups() {
            return groups;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }
    }

    @Override
    public String toString() {
        return "FaceUserBean{" +
                "result=" + result +
                ", log_id=" + log_id +
                '}';
    }
}
