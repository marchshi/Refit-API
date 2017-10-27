package com.smq.demo5.easemob;

/**
 * This interface is created for RestAPI of User Integration, it should be
 * synchronized with the API list.
 * 
 * @author Eric23 2016-01-05
 * @see http://docs.easemob.com/
 */
public interface IMUserAPI {

	/**
	 * 娉ㄥ唽IM鐢ㄦ埛[鍗曚釜] <br>
	 * POST
	 * 
	 * @param payload
	 *            <code>{"username":"${鐢ㄦ埛鍚峿","password":"${瀵嗙爜}", "nickname":"${鏄电О鍊紏"}</code>
	 * @return
	 */
	Object createNewIMUserSingle(Object payload);

	/**
	 * 娉ㄥ唽IM鐢ㄦ埛[鎵归噺] <br>
	 * POST
	 * 
	 * @param payload
	 *            <code>[{"username":"${鐢ㄦ埛鍚�}","password":"${瀵嗙爜}"},鈥�{"username":"${鐢ㄦ埛鍚�}","password":"${瀵嗙爜}"}]</code>
	 * @return
	 */
	Object createNewIMUserBatch(Object payload);

	/**
	 * 鑾峰彇IM鐢ㄦ埛[鍗曚釜] <br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object getIMUserByUserName(String userName);

	/**
	 * 鑾峰彇IM鐢ㄦ埛[鎵归噺]锛屽弬鏁颁负绌烘椂榛樿杩斿洖鏈�棭鍒涘缓鐨�0涓敤鎴�<br>
	 * GET
	 * 
	 * @param limit
	 *            鍗曢〉鑾峰彇鏁伴噺
	 * @param cursor
	 *            娓告爣锛屽ぇ浜庡崟椤佃褰曟椂浼氫骇鐢�
	 * @return
	 */
	Object getIMUsersBatch(Long limit, String cursor);

	/**
	 * 鍒犻櫎IM鐢ㄦ埛[鍗曚釜] <br>
	 * DELETE
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object deleteIMUserByUserName(String userName);

	/**
	 * 鍒犻櫎IM鐢ㄦ埛[鎵归噺]锛岄殢鏈哄垹闄�<br>
	 * DELETE
	 * 
	 * @param limit
	 *            鍒犻櫎鏁伴噺锛屽缓璁�00-500
	 * @return
	 */
	Object deleteIMUserBatch(Long limit, String cursor);

	/**
	 * 閲嶇疆IM鐢ㄦ埛瀵嗙爜 <br>
	 * PUT
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param payload
	 *            <code>{"newpassword" : "${鏂板瘑鐮佹寚瀹氱殑瀛楃涓瞹"}</code>
	 * @return
	 */
	Object modifyIMUserPasswordWithAdminToken(String userName, Object payload);

	/**
	 * 淇敼鐢ㄦ埛鏄电О <br>
	 * PUT
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param payload
	 *            <code>{"nickname" : "${鏄电О鍊紏"}</code>
	 * @return
	 */
	Object modifyIMUserNickNameWithAdminToken(String userName, Object payload);

	/**
	 * 缁橧M鐢ㄦ埛鐨勬坊鍔犲ソ鍙�<br>
	 * POST
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param friendName
	 *            濂藉弸鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object addFriendSingle(String userName, String friendName);

	/**
	 * 瑙ｉ櫎IM鐢ㄦ埛鐨勫ソ鍙嬪叧绯�<br>
	 * DELETE
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param friendName
	 *            濂藉弸鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object deleteFriendSingle(String userName, String friendName);

	/**
	 * 鏌ョ湅鏌愪釜IM鐢ㄦ埛鐨勫ソ鍙嬩俊鎭�<br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object getFriends(String userName);

	/**
	 * 鑾峰彇IM鐢ㄦ埛鐨勯粦鍚嶅崟 <br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object getBlackList(String userName);

	/**
	 * 寰�M鐢ㄦ埛鐨勯粦鍚嶅崟涓姞浜�<br>
	 * POST
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param payload
	 *            <code>{"usernames":["5cxhactgdj", "mh2kbjyop1"]}</code>
	 * @return
	 */
	Object addToBlackList(String userName, Object payload);

	/**
	 * 浠嶪M鐢ㄦ埛鐨勯粦鍚嶅崟涓噺浜�<br>
	 * DELETE
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param blackListName
	 *            榛戝悕鍗曠敤鎴跺悕鎴栫敤鎴禝D
	 * @return
	 */
	Object removeFromBlackList(String userName, String blackListName);

	/**
	 * 鏌ョ湅鐢ㄦ埛鍦ㄧ嚎鐘舵� <br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object getIMUserStatus(String userName);

	/**
	 * 鏌ヨ绂荤嚎娑堟伅鏁�<br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object getOfflineMsgCount(String userName);

	/**
	 * 鏌ヨ鏌愭潯绂荤嚎娑堟伅鐘舵� <br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @param msgId
	 *            娑堟伅ID
	 * @return
	 */
	Object getSpecifiedOfflineMsgStatus(String userName, String msgId);

	/**
	 * 鐢ㄦ埛璐﹀彿绂佺敤 <br>
	 * POST
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object deactivateIMUser(String userName);

	/**
	 * 鐢ㄦ埛璐﹀彿瑙ｇ <br>
	 * POST
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object activateIMUser(String userName);

	/**
	 * 寮哄埗鐢ㄦ埛涓嬬嚎 <br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 */
	Object disconnectIMUser(String userName);

	/**
	 * 鑾峰彇鐢ㄦ埛鍙備笌鐨勭兢缁�<br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 * @see http://docs.easemob.com/doku.php?id=start:100serverintegration:
	 *      60groupmgmt
	 */
	Object getIMUserAllChatGroups(String userName);

	/**
	 * 鑾峰彇鐢ㄦ埛鎵�湁鍙備笌鐨勮亰澶╁ <br>
	 * GET
	 * 
	 * @param userName
	 *            鐢ㄦ埗鍚嶆垨鐢ㄦ埗ID
	 * @return
	 * @see http://docs.easemob.com/doku.php?id=start:100serverintegration:
	 *      70chatroommgmt
	 */
	Object getIMUserAllChatRooms(String userName);
}
