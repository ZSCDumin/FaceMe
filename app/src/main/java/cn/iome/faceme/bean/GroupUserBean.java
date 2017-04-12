package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.util.FaceManager;

/**
 * Created by haoping on 17/4/10.
 * {@link FaceManager#getGroupUsers(java.lang.String, java.util.HashMap, FaceManager.Callback)}
 */
public class GroupUserBean {

    /**
     * result_num : 3
     * result : [{"uid":"dq65454dddd","user_info":"test_user_info"},{"uid":"haoping2","user_info":"test_user_info"},{"uid":"uid1","user_info":"test_user_info"}]
     * log_id : 558300064
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
         * uid : dq65454dddd
         * user_info : test_user_info
         */

        private String uid;
        private String user_info;

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
    }

    @Override
    public String toString() {
        return "GroupUserBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}';
    }
}
