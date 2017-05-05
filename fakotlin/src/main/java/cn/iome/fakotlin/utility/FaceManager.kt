package cn.iome.fakotlin.utility

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import cn.iome.fakotlin.BuildConfig
import cn.iome.fakotlin.bean.*
import cn.iome.fakotlin.function.Consumer
import com.baidu.aip.face.AipFace
import com.google.gson.Gson
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by haoping on 17/4/8.
 * 百度人脸识别工具类
 */
class FaceManager private constructor() {
    private val threadPool: ExecutorService
    private val client: AipFace
    private val gson: Gson
    private val handler: Handler

    init {
        // 初始化一个FaceClient
        client = AipFace(Constants.APP_ID, Constants.API_KEY, Constants.SECRET_KEY)
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000)//建立连接的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000)//通过打开的连接传输数据的超时时间（单位：毫秒）
        threadPool = Executors.newSingleThreadExecutor()
        gson = Gson()
        handler = Handler(Looper.getMainLooper())
    }

    /**
     * 快速入门
     */
    fun quick(imagePath: String, callback: Consumer<QuickBean>) {
        threadPool.execute {
            val res = client.detect(imagePath, HashMap<String, String>())
            val quickBean = gson.fromJson(res.toString(), QuickBean::class.java)
            handler.post { callback.accept(quickBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "quick: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸检测
     * 自定义参数定义
     * final HashMap<String></String>, String> options = new HashMap<>();
     * options.put("max_face_num", "5");
     * options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
     * 人脸检测 请求参数详情
     * 参数	                类型	        描述          	是否必须
     * face_fields	        Boolean	    包括age、beauty、expression、faceshape、gender、glasses、landmark、race、qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度。	否
     * max_face_num	        unit32	    最多处理人脸数目，默认值1	是
     * image	            String	    图像数据，支持本地图像文件路径，图像文件二进制数组	是
     * 人脸检测 返回数据参数详情
     * 参数	                类型	        是否一定输出	描述
     * log_id	            uint64	    是	        日志id
     * result_num	        Int	        是	        人脸数目
     * result	            object[]    是	        人脸属性对象的集合
     * +age	                double	    否	        年龄。face_fields包含age时返回
     * +beauty	            double	    否	        美丑打分，范围0-1，越大表示越美。face_fields包含beauty时返回
     * +location	        object	    是	        人脸在图片中的位置
     * ++left	            Int	        是	        人脸区域离左边界的距离
     * ++top	            Int	        是	        人脸区域离上边界的距离
     * ++width	            Int	        是	        人脸区域的宽度
     * ++height	            Int	        是	        人脸区域的高度
     * +face_probability	double	    是	        人脸置信度，范围0-1
     * +rotation_angle	    int32	    是	        人脸框相对于竖直方向的顺时针旋转角，[-180,180]
     * +yaw	                double	    是	        三维旋转之左右旋转角[-90(左), 90(右)]
     * +pitch	            double	    是	        三维旋转之俯仰角度[-90(上), 90(下)]
     * +roll	            double	    是	        平面内旋转角[-180(逆时针), 180(顺时针)]
     * +expression	        Int	        否	        表情，0，不笑；1，微笑；2，大笑。face_fields包含expression时返回
     * +expression_probability	double	否	        表情置信度，范围0~1。face_fields包含expression时返回
     * +faceshape	        object[]    否	        脸型置信度。face_fields包含faceshape时返回
     * ++type	            String	    是	        脸型：square/triangle/oval/heart/round
     * ++probability	    double	    是	        置信度：0~1
     * +gender	            String	    否	        male、female。face_fields包含gender时返回
     * +gender_probability	double	    否	        性别置信度，范围0~1。face_fields包含gender时返回
     * +glasses	            Int	否	    是否带眼镜，0-无眼镜，1-普通眼镜，2-墨镜。face_fields包含glasses时返回
     * +glasses_probability	double	    否	        眼镜置信度，范围0~1。face_fields包含glasses时返回
     * +landmark	        object[]    否	        4个关键点位置，左眼中心、右眼中心、鼻尖、嘴中心。face_fields包含landmark时返回
     * ++x	                Int	        否	        x坐标
     * ++y	                Int	        否	        y坐标
     * +landmark72	        object[]    否	        72个特征点位置，示例图 。face_fields包含landmark时返回
     * ++x	                Int	        否	        x坐标
     * ++y	                Int	        否	        y坐标
     * +race	            String	    否	        yellow、white、black、arabs。face_fields包含race时返回
     * +race_probability	double	    否	        人种置信度，范围0~1。face_fields包含race时返回
     * +qualities	        object	    否	        人脸质量信息。face_fields包含qualities时返回
     * ++occlusion	        object	    是	        人脸各部分遮挡的概率， [0, 1] （待上线）
     * +++left_eye	        double	    是	        左眼
     * +++right_eye	        double	    是	        右眼
     * +++nose	            double	    是	        鼻子
     * +++mouth	            double	    是	        嘴
     * +++left_cheek	    double	    是	        左脸颊
     * +++right_cheek	    double	    是	        右脸颊
     * +++chin	            double	    是	        下巴
     * ++type	            object	    是	        真实人脸/卡通人脸置信度
     * +++human	            double	    是	        真实人脸置信度，[0, 1]
     * +++cartoon	        double	    是	        卡通人脸置信度，[0, 1]

     * @param path    图片路径
     * *
     * @param options 自定义参数
     */
    fun faceRecognizeWithPath(path: String, options: HashMap<String, String>, callback: Consumer<RecognizeBean>) {
        threadPool.execute {
            // 参数为本地图片路径
            val response = client.detect(path, options)
            val recognizeBean = gson.fromJson(response.toString(), RecognizeBean::class.java)
            handler.post { callback.accept(recognizeBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "faceRecognizeWithPath: " + response.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸检测
     * 自定义参数定义
     * final HashMap<String></String>, String> options = new HashMap<>();
     * options.put("max_face_num", "5");
     * options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
     * 人脸检测 请求参数详情
     * 参数	                类型	        描述          	是否必须
     * face_fields	        Boolean	    包括age、beauty、expression、faceshape、gender、glasses、landmark、race、qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度。	否
     * max_face_num	        unit32	    最多处理人脸数目，默认值1	是
     * image	            String	    图像数据，支持本地图像文件路径，图像文件二进制数组	是
     * 人脸检测 返回数据参数详情
     * 参数	                类型	        是否一定输出	描述
     * log_id	            uint64	    是	        日志id
     * result_num	        Int	        是	        人脸数目
     * result	            object[]    是	        人脸属性对象的集合
     * +age	                double	    否	        年龄。face_fields包含age时返回
     * +beauty	            double	    否	        美丑打分，范围0-1，越大表示越美。face_fields包含beauty时返回
     * +location	        object	    是	        人脸在图片中的位置
     * ++left	            Int	        是	        人脸区域离左边界的距离
     * ++top	            Int	        是	        人脸区域离上边界的距离
     * ++width	            Int	        是	        人脸区域的宽度
     * ++height	            Int	        是	        人脸区域的高度
     * +face_probability	double	    是	        人脸置信度，范围0-1
     * +rotation_angle	    int32	    是	        人脸框相对于竖直方向的顺时针旋转角，[-180,180]
     * +yaw	                double	    是	        三维旋转之左右旋转角[-90(左), 90(右)]
     * +pitch	            double	    是	        三维旋转之俯仰角度[-90(上), 90(下)]
     * +roll	            double	    是	        平面内旋转角[-180(逆时针), 180(顺时针)]
     * +expression	        Int	        否	        表情，0，不笑；1，微笑；2，大笑。face_fields包含expression时返回
     * +expression_probability	double	否	        表情置信度，范围0~1。face_fields包含expression时返回
     * +faceshape	        object[]    否	        脸型置信度。face_fields包含faceshape时返回
     * ++type	            String	    是	        脸型：square/triangle/oval/heart/round
     * ++probability	    double	    是	        置信度：0~1
     * +gender	            String	    否	        male、female。face_fields包含gender时返回
     * +gender_probability	double	    否	        性别置信度，范围0~1。face_fields包含gender时返回
     * +glasses	            Int	否	    是否带眼镜，0-无眼镜，1-普通眼镜，2-墨镜。face_fields包含glasses时返回
     * +glasses_probability	double	    否	        眼镜置信度，范围0~1。face_fields包含glasses时返回
     * +landmark	        object[]    否	        4个关键点位置，左眼中心、右眼中心、鼻尖、嘴中心。face_fields包含landmark时返回
     * ++x	                Int	        否	        x坐标
     * ++y	                Int	        否	        y坐标
     * +landmark72	        object[]    否	        72个特征点位置，示例图 。face_fields包含landmark时返回
     * ++x	                Int	        否	        x坐标
     * ++y	                Int	        否	        y坐标
     * +race	            String	    否	        yellow、white、black、arabs。face_fields包含race时返回
     * +race_probability	double	    否	        人种置信度，范围0~1。face_fields包含race时返回
     * +qualities	        object	    否	        人脸质量信息。face_fields包含qualities时返回
     * ++occlusion	        object	    是	        人脸各部分遮挡的概率， [0, 1] （待上线）
     * +++left_eye	        double	    是	        左眼
     * +++right_eye	        double	    是	        右眼
     * +++nose	            double	    是	        鼻子
     * +++mouth	            double	    是	        嘴
     * +++left_cheek	    double	    是	        左脸颊
     * +++right_cheek	    double	    是	        右脸颊
     * +++chin	            double	    是	        下巴
     * ++type	            object	    是	        真实人脸/卡通人脸置信度
     * +++human	            double	    是	        真实人脸置信度，[0, 1]
     * +++cartoon	        double	    是	        卡通人脸置信度，[0, 1]

     * @param Bytes   图片二进制数组
     * *
     * @param options 自定义参数
     */
    fun faceRecognizeWithBytes(Bytes: ByteArray, options: HashMap<String, String>, callback: Consumer<RecognizeBean>) {
        threadPool.execute {
            val response = client.detect(Bytes, options)
            val recognizeBean = gson.fromJson(response.toString(), RecognizeBean::class.java)
            handler.post { callback.accept(recognizeBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "faceRecognizeWithBytes: " + response.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸对比：人脸比对接口提供了对上传图片进行两两比对，并输出相似度得分的功能。
     * 接受的参数为一系列本地图片路径的数组。
     * 参数为本地图片路径：
     * ArrayList<String> pathArray = new ArrayList<>();
     * pathArray.add(Constants.test1);
     * pathArray.add(Constants.test2);
     * pathArray.add(Constants.test1);
     * 返回数据参数详情：
     * log_id	    是	uint64	        请求标识码，随机数，唯一
     * result_num	是	uint32	        返回结果数目，即：result数组中元素个数
     * result	    是	array(object)	结果数据，index和请求图片index对应。数组元素为每张图片的匹配得分数组，top n。 得分[0,100.0]
     * +index_i	    是	uint32	        比对图片1的index
     * +index_j	    是	uint32	        比对图片2的index
     * +score	    是	double	        比对得分

     * @param pathArray 本地图片路径集合
    </String> */
    fun faceRecognizeEach(pathArray: ArrayList<String>, callback: Consumer<RecognizeEachBean>) {
        threadPool.execute {
            val response = client.match(pathArray)
            val recognizeEachBean = gson.fromJson(response.toString(), RecognizeEachBean::class.java)
            handler.post { callback.accept(recognizeEachBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "faceRecognizeEach: " + response.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸注册：人脸注册接口提供了使用上传图片进行注册新用户的功能，需要指定注册用户的id和描述信息，所在组id以及本地用户人脸图片。
     * 注：每个用户（uid）所能注册的最大人脸数量为5张。
     * JSONObject res = client.addUser("dq65454dddd", "test_user_info", "group1", path);
     * 参数为本地图片路径
     * ArrayList<String> path = new ArrayList<>();
     * path.add(Constants.test1);
     * path.add(Constants.test2);
     * 返回样例：
     * // 注册成功
     * {
     * "log_id": 73473737,
     * }
     * // 注册发生错误
     * {
     * "error_code": 216616,
     * "log_id": 674786177,
     * "error_msg": "image exist"
     * }

     * @param uid      用户ID
     * *
     * @param userInfo 用户信息
     * *
     * @param groupId  用户需要添加组
     * *
     * @param imgPaths 用户头像集合
    </String> */
    fun facesetAddUser(uid: String, userInfo: String, groupId: String, imgPaths: ArrayList<String>, callback: Consumer<FaceResultBean>) {
        threadPool.execute {
            val res = client.addUser(uid, userInfo, groupId, imgPaths)
            val faceResultBean = gson.fromJson(res.toString(), FaceResultBean::class.java)
            handler.post { callback.accept(faceResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "facesetAddUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸更新:人脸更新接口提供了为已有用户更新人脸图像的功能，新上传的图片将覆盖原有图像。
     * 参数为本地图片路径
     * ArrayList<String> path = new ArrayList<>();
     * path.add(Constants.test1);
     * path.add(Constants.test2);
     * 返回样例：
     * // 更新成功
     * {
     * "log_id": 73473737,
     * }
     * // 更新发生错误
     * {
     * "error_code": 216612,
     * "log_id": 1137508902,
     * "error_msg": "user not exist"
     * }

     * @param uid  用户ID
     * *
     * @param path 用户头像集合
    </String> */
    fun facesetUpdateUser(uid: String, path: ArrayList<String>, callback: Consumer<FaceResultBean>) {
        threadPool.execute {
            val res = client.updateUser(uid, path)
            val faceResultBean = gson.fromJson(res.toString(), FaceResultBean::class.java)
            handler.post { callback.accept(faceResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "facesetUpdateUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }

    }

    /**
     * 人脸删除:人脸删除接口提供了从库中彻底删除一个用户的功能，包括用户所有图像和身份信息，同时也将从各个组中把用户删除。
     * 更新成功
     * {
     * "log_id": 73473737,
     * }
     * // 更新发生错误
     * {
     * "error_code": 216612,
     * "log_id": 1137508902,
     * "error_msg": "user not exist"
     * }

     * @param uid 用户ID
     */
    fun facesetDeleteUser(uid: String, callback: Consumer<FaceResultBean>) {
        threadPool.execute {
            val res = client.deleteUser(uid)
            val faceResultBean = gson.fromJson(res.toString(), FaceResultBean::class.java)
            handler.post { callback.accept(faceResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "facesetDeleteUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸认证:人脸认证接口用于识别上传的图片是否为指定用户。
     * ArrayList<String> path = new ArrayList<>();
     * path.add(Constants.test1);
     * path.add(Constants.test2);
     * HashMap<String></String>, Object> options = new HashMap<>(1);
     * options.put("top_num", path.size());
     * 人脸认证请求参数详情：
     * 参数	        是否必选	类型	            说明
     * uid	        是	    string	        用户id（由数字、字母、下划线组成），长度限制128B
     * images	    是	    string	        图像base64编码,多张图片半角逗号分隔，总共最大10M
     * top_num	    否	    uint32	        返回匹配得分top数，默认为1
     * 人脸认证返回数据参数详情：
     * 字段	        是否必选	类型      	    说明
     * log_id	    是	    uint64	        请求标识码，随机数，唯一
     * result_num	是	    uint32	        返回结果数目，即：result数组中元素个数
     * result	    是	    array(double)	结果数组，数组元素为匹配得分，top n。 得分范围[0,100.0]。得分超过80可认为认证成功
     * 返回样例：
     * {
     * "results": [
     * 93.86580657959,
     * 92.237548828125
     * ],
     * "result_num": 2,
     * "log_id": 1629483134
     * }

     * @param uid     用户ID
     * *
     * @param path    用户头像集合
     * *
     * @param options 用户认证参数
    </String> */
    fun verifyUser(uid: String, path: ArrayList<String>, options: HashMap<String, Any>, callback: Consumer<FaceResultBean>) {
        threadPool.execute {
            val res = client.verifyUser(uid, path, options)
            val faceResultBean = gson.fromJson(res.toString(), FaceResultBean::class.java)
            handler.post { callback.accept(faceResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "verifyUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 人脸识别:人脸识别接口用于计算指定组内用户与上传图像的相似度。
     * ArrayList<String> path = new ArrayList<>();
     * path.add(Constants.test1);
     * path.add(Constants.test2);
     * HashMap<String></String>, Object> options = new HashMap<>(1);
     * options.put("user_top_num", path.size());
     * options.put("face_top_num", 10);
     * 人脸识别请求参数详情：
     * 参数	        是否必选	类型	            说明
     * group_id	    是	    string	        用户组id（由数字、字母、下划线组成），长度限制128B
     * images	    是	    string	        图像base64编码,多张图片半角逗号分隔，总共最大10M
     * user_top_num	否	    uint32	        返回用户top数，默认为1
     * face_top_num	否	    uint32	        单用户人脸匹配得分top数，默认为1
     * 人脸识别返回数据参数详情：
     * 字段	        是否必选	类型	            说明
     * log_id	    是	    uint64	        请求标识码，随机数，唯一
     * result_num	是	    uint32	        返回结果数目，即：result数组中元素个数
     * result	    是	    array(double)	结果数组
     * +uid	        是	    string	        匹配到的用户id
     * +user_info	是	    string	        注册时的用户信息
     * +scores	    是	    array(double)	结果数组，数组元素为匹配得分，top n。 得分[0,100.0]

     * @param groupId 用户组ID
     * *
     * @param path    用户头像集合
     * *
     * @param options 用户认证参数
    </String> */
    fun identifyUser(groupId: String, path: ArrayList<String>, options: HashMap<String, Any>, callback: Consumer<IdentifyResultBean>) {
        threadPool.execute {
            val res = client.identifyUser(groupId, path, options)
            val identifyResultBean = gson.fromJson(res.toString(), IdentifyResultBean::class.java)
            handler.post { callback.accept(identifyResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "identifyUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }

    }

    /**
     * 用户信息查询:用户信息查询接口用于查询某用户的详细信息。
     * 用户信息查询返回数据参数详情：
     * log_id	    是	uint64	        请求标识码，随机数，唯一
     * result	    是	array(double)	结果数组
     * +uid	        是	string	        匹配到的用户id
     * +user_info	是	string	        注册时的用户信息
     * +groups	    是	array(string)	用户所属组列表

     * @param uid 用户ID
     */
    fun getUser(uid: String, callback: Consumer<FaceUserBean>) {
        threadPool.execute {
            val res = client.getUser(uid)
            val faceUserBean = gson.fromJson(res.toString(), FaceUserBean::class.java)
            handler.post { callback.accept(faceUserBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "getUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 组列表查询:组列表查询接口用于查询一个app下所有组的列表。
     * HashMap<String></String>, Object> options = new HashMap<>(2);
     * options.put("start", 0);
     * options.put("num", 10);
     * 组列表查询请求参数详情：
     * 参数	    是否必选	    类型	            说明
     * start	否	        uint32	        默认值0，起始序号
     * end	    否	        uint32	        返回数量，默认值100，最大值1000
     * 组列表查询返回数据参数详情：
     * 字段	        是否必选	类型	            说明
     * log_id	    是	    uint64	        请求标识码，随机数，唯一
     * result_num	是	    uint32	        返回个数
     * result	    是	    array(string)	group_id列表

     * @param options options
     */
    fun getGroupList(options: HashMap<String, Any>, callback: Consumer<GroupListBean>) {
        threadPool.execute {
            val res = client.getGroupList(options)
            val groupListBean = gson.fromJson(res.toString(), GroupListBean::class.java)
            handler.post { callback.accept(groupListBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "getGroupList: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 组内用户列表查询:组内用户列表查询接口用于查询一个用户组内所有的用户信息。
     * HashMap<String></String>, Object> options = new HashMap<>(2);
     * options.put("start", 0);
     * options.put("num", 10);
     * 组内用户列表查询请求参数详情：
     * 参数	    是否必选	类型	            说明
     * group_id	是	    string	        用户组id
     * start	否	    uint32	        默认值0，起始序号
     * end	    否	    uint32	        返回数量，默认值100，最大值1000
     * 组内用户列表查询返回数据参数详情：
     * 字段	    是否必选	类型	说明
     * log_id	是	    uint64	        请求标识码，随机数，唯一
     * result_num	是	uint32	        返回个数
     * result	是	    array(object)	user列表
     * +uid	    是	    string	        用户id
     * +user_info	是	string	        用户信息

     * @param groupId 组ID
     * *
     * @param options options
     */
    fun getGroupUsers(groupId: String, options: HashMap<String, Any>, callback: Consumer<GroupUserBean>) {
        threadPool.execute {
            val res = client.getGroupUsers(groupId, options)
            val groupUserBean = gson.fromJson(res.toString(), GroupUserBean::class.java)
            handler.post { callback.accept(groupUserBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "getGroupUsers: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 组内添加用户:组内添加用户接口用于把一个已经存在于库中的用户添加到新的用户组中。

     * @param groupId 组ID
     * *
     * @param uid     用户ID
     */
    fun addGroupUser(groupId: String, uid: String, callback: Consumer<FaceResultBean>) {
        threadPool.execute {
            val res = client.addGroupUser(groupId, uid)
            val faceResultBean = gson.fromJson(res.toString(), FaceResultBean::class.java)
            handler.post { callback.accept(faceResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "addGroupUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 组内删除用户：组内删除用户接口用于把一个用户从某个组中删除，但不会删除用户在其它组内的信息。当用户仅属于单个分组时，本接口将返回错误。

     * @param groupId 组ID
     * *
     * @param uid     用户ID
     */
    fun deleteGroupUser(groupId: String, uid: String, callback: Consumer<FaceResultBean>) {
        threadPool.execute {
            val res = client.deleteGroupUser(groupId, uid)
            val faceResultBean = gson.fromJson(res.toString(), FaceResultBean::class.java)
            handler.post { callback.accept(faceResultBean) }
            if (BuildConfig.DEBUG) {
                try {
                    Log.i(TAG, "deleteGroupUser: " + res.toString(2))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }

    }

    /**
     * 将文件转成字节

     * @param imagePath 文件路径
     * *
     * @return bytes
     */
    fun readImageFile(imagePath: String): ByteArray {
        var bytes = ByteArray(0)
        try {
            val fis = FileInputStream(imagePath)
            val baos = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            while (fis.read(buffer) != -1) {
                baos.write(buffer, 0, buffer.size)
            }
            bytes = baos.toByteArray()
            fis.close()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bytes
    }

    /**
     * 返回拍摄的照片路径
     * @return 照片路径
     */
    var photoPath: String? = null
        private set

    /**
     * 创建拍照文件
     * @param context context
     * *
     * @return 照片文件
     * *
     * @throws IOException IOException
     */
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.SIMPLIFIED_CHINESE).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        )

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.absolutePath
        return image
    }

    companion object Factory{

        private val TAG = FaceManager::class.java.simpleName
        fun creat(): FaceManager = FaceManager()

    }


}
