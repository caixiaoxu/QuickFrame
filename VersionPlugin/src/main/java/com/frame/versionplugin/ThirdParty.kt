/**
 * @author haizhuo
 * @desciption 第三方依赖包
 */
object ThirdPart {
    //网路请求库retrofit
    val retrofit = Retrofit
    object Retrofit {
        private const val retrofit_version = "2.8.1"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
        const val convertGson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
        const val adapterRxjava = "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"
    }

    //图片加载框架
    object Glide {
        private const val glide_version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$glide_version"
        const val compiler = "com.github.bumptech.glide:compiler:$glide_version"
    }
}