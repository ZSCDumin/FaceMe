package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.util.FaceUtil;

/**
 * Created by haoping on 17/4/10.
 * {@link FaceUtil#verifyUser(java.lang.String, java.util.ArrayList, java.util.HashMap, FaceUtil.Callback)}
 * {@link FaceUtil#facesetDeleteUser(java.lang.String, FaceUtil.Callback)}
 * {@link FaceUtil#facesetUpdateUser(java.lang.String, java.util.ArrayList, FaceUtil.Callback)}
 * {@link FaceUtil#facesetAddUser(java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList, FaceUtil.Callback)}
 * {@link FaceUtil#deleteGroupUser(java.lang.String, java.lang.String, FaceUtil.Callback)}
 * {@link FaceUtil#addGroupUser(java.lang.String, java.lang.String, FaceUtil.Callback)}
 */
public class FaceResultBean {

    /**
     * error_code : 216616
     * error_msg : image exist
     * log_id : 2733216045
     */

    private long log_id;
    private String error_msg;
    private int error_code;
    /**
     * result_num : 2
     * results : [100,100]
     */

    private int result_num;
    private List<Integer> results;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "FaceResultBean{" +
                "log_id=" + log_id +
                ", error_msg='" + error_msg + '\'' +
                ", error_code=" + error_code +
                ", result_num=" + result_num +
                ", results=" + results +
                '}';
    }
}
