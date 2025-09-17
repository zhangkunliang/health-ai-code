import com.ai.healthaicode.constant.AppConstant;
import com.ai.healthaicode.exception.BusinessException;
import com.ai.healthaicode.exception.ErrorCode;
import com.ai.healthaicode.exception.ThrowUtils;
import com.ai.healthaicode.model.entity.App;
import com.ai.healthaicode.model.entity.User;
import com.ai.healthaicode.service.AppService;
import com.ai.healthaicode.service.ProjectDownloadService;
import com.ai.healthaicode.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;

@Resource
private ProjectDownloadService projectDownloadService;

@Resource
private UserService userService;

@Resource
private AppService appService;

/**
 * 下载应用代码
 *
 * @param appId    应用ID
 * @param request  请求
 * @param response 响应
 */
@GetMapping("/download/{appId}")
public void downloadAppCode(@PathVariable Long appId,
                            HttpServletRequest request,
                            HttpServletResponse response) {
    // 1. 基础校验
    ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID无效");
    // 2. 查询应用信息
    App app = appService.getById(appId);
    ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
    // 3. 权限校验：只有应用创建者可以下载代码
    User loginUser = userService.getLoginUser(request);
    if (!app.getUserId().equals(loginUser.getId())) {
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限下载该应用代码");
    }
    // 4. 构建应用代码目录路径（生成目录，非部署目录）
    String codeGenType = app.getCodeGenType();
    String sourceDirName = codeGenType + "_" + appId;
    String sourceDirPath = AppConstant.CODE_OUTPUT_ROOT_DIR + File.separator + sourceDirName;
    // 5. 检查代码目录是否存在
    File sourceDir = new File(sourceDirPath);
    ThrowUtils.throwIf(!sourceDir.exists() || !sourceDir.isDirectory(),
            ErrorCode.NOT_FOUND_ERROR, "应用代码不存在，请先生成代码");
    // 6. 生成下载文件名（不建议添加中文内容）
    String downloadFileName = String.valueOf(appId);
    // 7. 调用通用下载服务
    projectDownloadService.downloadProjectAsZip(sourceDirPath, downloadFileName, response);
}

