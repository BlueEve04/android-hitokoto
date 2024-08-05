package com.blueeve.dailyhitokoto

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * 从 JSON 文件中提取特定 id 下的字段
 *
 * @param context 上下文
 * @param fileName JSON 文件名
 * @param targetId 要查找的 id
 * @return 提取到的字段值，格式为 Map<String, String> 或 null（如果未找到或出错）
 * @throws JSONException 如果 JSON 解析出错
 */
fun extractDataById(context: Context, fileName: String, targetId: Int): Map<String, String>? {
    val jsonString: String
    try {
        // 读取文件内容
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

    return try {
        // 解析 JSON 数据
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.getInt("id") == targetId) {
                // 提取字段并返回
                val result = mutableMapOf<String, String>()
                result["hitokoto"] = jsonObject.getString("hitokoto")
                result["from"] = jsonObject.getString("from")
                result["from_who"] = jsonObject.optString("from_who", "未知") // 使用 optString 处理可能为 null 的字段
                return result
            }
        }
        null
    } catch (e: JSONException) {
        e.printStackTrace()
        null
    }
}
