package com.ai.healthaicode.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ai.healthaicode.constant.UserConstant;
import com.ai.healthaicode.exception.ErrorCode;
import com.ai.healthaicode.exception.ThrowUtils;
import com.ai.healthaicode.model.dto.chathistory.ChatHistoryQueryRequest;
import com.ai.healthaicode.model.entity.App;
import com.ai.healthaicode.model.entity.User;
import com.ai.healthaicode.service.AppService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ai.healthaicode.mapper.ChatHistoryMapper;
import com.ai.healthaicode.model.entity.ChatHistory;
import com.ai.healthaicode.model.enums.ChatHistoryMessageTypeEnum;
import com.ai.healthaicode.service.ChatHistoryService;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 对话历史 服务层实现。
 *
 * @author <a href="https://github.com/zhangkunliang">张坤梁</a>
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory> implements ChatHistoryService {

    @Resource
    @Lazy
    private AppService appService;

    /**
     * 保存用户消息
     *
     * @param appId   应用ID
     * @param userId  用户ID
     * @param message 消息内容
     * @return 保存的聊天历史记录
     */
    @Override
    public ChatHistory saveUserMessage(Long appId, Long userId, String message) {
        ChatHistory chatHistory = ChatHistory.builder()
                .appId(appId)
                .userId(userId)
                .message(message)
                .messageType(ChatHistoryMessageTypeEnum.USER.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(chatHistory);
        return chatHistory;
    }

    /**
     * 保存AI消息
     *
     * @param appId   应用ID
     * @param userId  用户ID
     * @param message 消息内容
     * @return 保存的聊天历史记录
     */
    @Override
    public ChatHistory saveAiMessage(Long appId, Long userId, String message) {
        ChatHistory chatHistory = ChatHistory.builder()
                .appId(appId)
                .userId(userId)
                .message(message)
                .messageType(ChatHistoryMessageTypeEnum.AI.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(chatHistory);
        return chatHistory;
    }

    /**
     * 保存AI错误消息
     *
     * @param appId   应用ID
     * @param userId  用户ID
     * @param message 错误信息
     * @return 保存的聊天历史记录
     */
    @Override
    public ChatHistory saveAiErrorMessage(Long appId, Long userId, String message) {
        ChatHistory chatHistory = ChatHistory.builder()
                .appId(appId)
                .userId(userId)
                .message(message)
                .messageType(ChatHistoryMessageTypeEnum.AI.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        this.save(chatHistory);
        return chatHistory;
    }

    /**
     * 获取应用的聊天历史记录（分页，最新10条）
     *
     * @param appId 应用ID
     * @return 聊天历史记录列表
     */
    @Override
    public List<ChatHistory> getAppChatHistory(Long appId) {
        return this.list(new QueryWrapper()
                .eq("appId", appId)
                .orderBy("createTime", "desc")
                .limit(10));
    }

    /**
     * 获取应用的聊天历史记录（分页，游标查询）
     *
     * @param appId          应用ID
     * @param lastCreateTime 最后一条记录的创建时间
     * @param pageSize       页面大小
     * @return 聊天历史记录列表
     */
    @Override
    public List<ChatHistory> getAppChatHistoryWithCursor(Long appId, LocalDateTime lastCreateTime, int pageSize) {
        QueryWrapper queryWrapper = new QueryWrapper()
                .eq("appId", appId);

        if (lastCreateTime != null) {
            queryWrapper.and("createTime < {0}", lastCreateTime);
        }

        return this.list(queryWrapper
                .orderBy("createTime", "desc")
                .limit(pageSize));
    }

    /**
     * 管理员分页查询所有聊天历史记录
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return 分页结果
     */
    @Override
    public Page<ChatHistory> listAllChatHistoryByPage(long pageNum, long pageSize) {
        return this.page(new Page<>(pageNum, pageSize),
                new QueryWrapper().orderBy("createTime", "desc"));
    }

    @Override
    public boolean addChatMessage(Long appId, String message, String messageType, Long userId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(message), ErrorCode.PARAMS_ERROR, "消息内容不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(messageType), ErrorCode.PARAMS_ERROR, "消息类型不能为空");
        ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        // 验证消息类型是否有效
        ChatHistoryMessageTypeEnum messageTypeEnum = ChatHistoryMessageTypeEnum.getEnumByValue(messageType);
        ThrowUtils.throwIf(messageTypeEnum == null, ErrorCode.PARAMS_ERROR, "不支持的消息类型: " + messageType);
        ChatHistory chatHistory = ChatHistory.builder()
                .appId(appId)
                .message(message)
                .messageType(messageType)
                .userId(userId)
                .build();
        return this.save(chatHistory);
    }

    @Override
    public boolean deleteByAppId(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq("appId", appId);
        return this.remove(queryWrapper);
    }

    /**
     * 获取查询包装类
     *
     * @param chatHistoryQueryRequest
     * @return
     */
    @Override
    public QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (chatHistoryQueryRequest == null) {
            return queryWrapper;
        }
        Long id = chatHistoryQueryRequest.getId();
        String message = chatHistoryQueryRequest.getMessage();
        String messageType = chatHistoryQueryRequest.getMessageType();
        Long appId = chatHistoryQueryRequest.getAppId();
        Long userId = chatHistoryQueryRequest.getUserId();
        LocalDateTime lastCreateTime = chatHistoryQueryRequest.getLastCreateTime();
        String sortField = chatHistoryQueryRequest.getSortField();
        String sortOrder = chatHistoryQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq("id", id)
                .like("message", message)
                .eq("messageType", messageType)
                .eq("appId", appId)
                .eq("userId", userId);
        // 游标查询逻辑 - 只使用 createTime 作为游标
        if (lastCreateTime != null) {
            queryWrapper.lt("createTime", lastCreateTime);
        }
        // 排序
        if (StrUtil.isNotBlank(sortField)) {
            queryWrapper.orderBy(sortField, "ascend".equals(sortOrder));
        } else {
            // 默认按创建时间降序排列
            queryWrapper.orderBy("createTime", false);
        }
        return queryWrapper;
    }

    @Override
    public Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                                      LocalDateTime lastCreateTime,
                                                      User loginUser) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(pageSize <= 0 || pageSize > 50, ErrorCode.PARAMS_ERROR, "页面大小必须在1-50之间");
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);
        // 验证权限：只有应用创建者和管理员可以查看
        App app = appService.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        boolean isAdmin = UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole());
        boolean isCreator = app.getUserId().equals(loginUser.getId());
        ThrowUtils.throwIf(!isAdmin && !isCreator, ErrorCode.NO_AUTH_ERROR, "无权查看该应用的对话历史");
        // 构建查询条件
        ChatHistoryQueryRequest queryRequest = new ChatHistoryQueryRequest();
        queryRequest.setAppId(appId);
        queryRequest.setLastCreateTime(lastCreateTime);
        QueryWrapper queryWrapper = this.getQueryWrapper(queryRequest);
        // 查询数据
        return this.page(Page.of(1, pageSize), queryWrapper);
    }

}