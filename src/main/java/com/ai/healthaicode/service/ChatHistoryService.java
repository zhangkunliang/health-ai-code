package com.ai.healthaicode.service;

import com.ai.healthaicode.model.dto.chathistory.ChatHistoryQueryRequest;
import com.ai.healthaicode.model.entity.ChatHistory;
import com.ai.healthaicode.model.entity.User;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 对话历史 服务层。
 *
 * @author <a href="https://github.com/zhangkunliang">张坤梁</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {
    
    /**
     * 保存用户消息
     *
     * @param appId   应用ID
     * @param userId  用户ID
     * @param message 消息内容
     * @return 保存的聊天历史记录
     */
    ChatHistory saveUserMessage(Long appId, Long userId, String message);
    
    /**
     * 保存AI消息
     *
     * @param appId   应用ID
     * @param userId  用户ID
     * @param message 消息内容
     * @return 保存的聊天历史记录
     */
    ChatHistory saveAiMessage(Long appId, Long userId, String message);
    
    /**
     * 保存AI错误消息
     *
     * @param appId   应用ID
     * @param userId  用户ID
     * @param message 错误信息
     * @return 保存的聊天历史记录
     */
    ChatHistory saveAiErrorMessage(Long appId, Long userId, String message);
    
    /**
     * 获取应用的聊天历史记录（最新10条）
     *
     * @param appId 应用ID
     * @return 聊天历史记录列表
     */
    List<ChatHistory> getAppChatHistory(Long appId);
    
    /**
     * 获取应用的聊天历史记录（游标分页）
     *
     * @param appId 应用ID
     * @param lastCreateTime 最后一条记录的创建时间
     * @param pageSize 页面大小
     * @return 聊天历史记录列表
     */
    List<ChatHistory> getAppChatHistoryWithCursor(Long appId, LocalDateTime lastCreateTime, int pageSize);
    
    /**
     * 管理员分页查询所有聊天历史记录
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return 分页结果
     */
    Page<ChatHistory> listAllChatHistoryByPage(long pageNum, long pageSize);

    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    boolean deleteByAppId(Long appId);

    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);

    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

}