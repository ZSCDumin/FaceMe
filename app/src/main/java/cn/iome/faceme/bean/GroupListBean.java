package cn.iome.faceme.bean;

import java.util.List;

import cn.iome.faceme.util.FaceUtil;

/**
 * Created by haoping on 17/4/10.
 * {@link FaceUtil#getGroupList(java.util.HashMap, FaceUtil.Callback)}
 */
public class GroupListBean {

    /**
     * result_num : 1
     * result : ["group1"]
     * log_id : 1280748993
     */

    private int result_num;
    private int log_id;
    private List<String> result;

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "GroupListBean{" +
                "result_num=" + result_num +
                ", log_id=" + log_id +
                ", result=" + result +
                '}';
    }
}
