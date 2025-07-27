package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * API Group 6: Social Features (Friends, Search, Following)
 * Base URL: https://x8ki-letl-twmt.n7.xano.io/api:mARu56Sc
 */
interface SocialApiService {
    
    @POST("search")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Body request: SearchRequest
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @GET("search/suggestions")
    suspend fun getSuggestedUsers(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<List<User>>>
    
    @POST("follow")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Body request: FollowRequest
    ): Response<ApiResponse<FollowResponse>>
    
    @POST("unfollow")
    suspend fun unfollowUser(
        @Header("Authorization") token: String,
        @Body request: FollowRequest
    ): Response<ApiResponse<Any>>
    
    @GET("followers")
    suspend fun getFollowers(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @GET("following")
    suspend fun getFollowing(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @GET("friendship/status")
    suspend fun getFriendshipStatus(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int
    ): Response<ApiResponse<FriendshipStatus>>
    
    @POST("friendship/request")
    suspend fun sendFriendRequest(
        @Header("Authorization") token: String,
        @Body request: FriendRequestModel
    ): Response<ApiResponse<FriendRequestModel>>
    
    @POST("friendship/accept")
    suspend fun acceptFriendRequest(
        @Header("Authorization") token: String,
        @Body request: AcceptFriendRequest
    ): Response<ApiResponse<Any>>
    
    @POST("friendship/decline")
    suspend fun declineFriendRequest(
        @Header("Authorization") token: String,
        @Body request: DeclineFriendRequest
    ): Response<ApiResponse<Any>>
    
    @GET("friendship/requests")
    suspend fun getFriendRequests(
        @Header("Authorization") token: String,
        @Query("type") type: String = "received", // received, sent
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<FriendRequestModel>>>
    
    @GET("friends")
    suspend fun getFriends(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @DELETE("friends/{userId}")
    suspend fun removeFriend(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Response<ApiResponse<Any>>
    
    @POST("block")
    suspend fun blockUser(
        @Header("Authorization") token: String,
        @Body request: BlockUserRequest
    ): Response<ApiResponse<Any>>
    
    @POST("unblock")
    suspend fun unblockUser(
        @Header("Authorization") token: String,
        @Body request: BlockUserRequest
    ): Response<ApiResponse<Any>>
    
    @GET("blocked")
    suspend fun getBlockedUsers(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<User>>>
    
    @GET("activity")
    suspend fun getActivityFeed(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<ApiResponse<PaginatedResponse<ActivityItem>>>
    
    @GET("mutual-friends")
    suspend fun getMutualFriends(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int,
        @Query("limit") limit: Int = 10
    ): Response<ApiResponse<List<User>>>
}

// Additional data classes for social features
data class FollowResponse(
    val isFollowing: Boolean,
    val followersCount: Int,
    val followingCount: Int
)

data class FriendshipStatus(
    val status: String, // none, following, friends, blocked, pending_sent, pending_received
    val isFollowing: Boolean,
    val isFollower: Boolean,
    val isFriend: Boolean,
    val isBlocked: Boolean,
    val pendingRequest: FriendRequestModel?
)

data class FriendRequestModel(
    val id: Int = 0,
    val fromUserId: Int,
    val toUserId: Int,
    val status: String = "pending", // pending, accepted, declined, expired
    val message: String = "",
    val expiresAt: String = "",
    val createdAt: String = "",
    val fromUser: User? = null,
    val toUser: User? = null
)

data class AcceptFriendRequest(
    val requestId: Int
)

data class DeclineFriendRequest(
    val requestId: Int,
    val reason: String = ""
)

data class BlockUserRequest(
    val userId: Int,
    val reason: String = ""
)

data class ActivityItem(
    val id: Int,
    val type: String, // follow, like, comment, friend_request, etc.
    val actorId: Int,
    val targetId: Int,
    val targetType: String, // user, reel, comment, etc.
    val message: String,
    val createdAt: String,
    val expiresAt: String,
    val actor: User?,
    val target: Any? // Can be User, Reel, etc.
)
package com.temporarysocial.app.data.api

import com.temporarysocial.app.data.model.*
import retrofit2.http.*

interface SocialApiService {
    
    @GET("social/search")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): ApiResponse<List<User>>
    
    @POST("social/follow")
    suspend fun followUser(
        @Header("Authorization") token: String,
        @Body request: Map<String, Int>
    ): ApiResponse<String>
    
    @GET("social/friends")
    suspend fun getFriends(@Header("Authorization") token: String): ApiResponse<List<User>>
    
    @GET("social/activity")
    suspend fun getActivity(@Header("Authorization") token: String): ApiResponse<List<Any>>
    
    @POST("social/block")
    suspend fun blockUser(
        @Header("Authorization") token: String,
        @Body request: Map<String, Int>
    ): ApiResponse<String>
}
