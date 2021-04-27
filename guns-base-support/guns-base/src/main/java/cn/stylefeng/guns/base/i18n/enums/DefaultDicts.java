package cn.stylefeng.guns.base.i18n.enums;

import lombok.Getter;

/**
 * 默认的翻译字段枚举
 *
 * @author fengshuonan
 * @Date 2020/3/18 13:24
 */
@Getter
public enum DefaultDicts {

    FIELD_POST_1("FIELD_POST", TranslationEnum.CHINESE, "职务"),
    FIELD_POST_2("FIELD_POST", TranslationEnum.ENGLISH, "post"),

    FIELD_PHONE_1("FIELD_PHONE", TranslationEnum.CHINESE, "手机号"),
    FIELD_PHONE_2("FIELD_PHONE", TranslationEnum.ENGLISH, "phone"),

    FIELD_CREATE_TIME_1("FIELD_CREATE_TIME", TranslationEnum.CHINESE, "创建时间"),
    FIELD_CREATE_TIME_2("FIELD_CREATE_TIME", TranslationEnum.ENGLISH, "create time"),

    FIELD_STATUS_1("FIELD_STATUS", TranslationEnum.CHINESE, "状态"),
    FIELD_STATUS_2("FIELD_STATUS", TranslationEnum.ENGLISH, "status"),

    FIELD_OPERATION_1("FIELD_OPERATION", TranslationEnum.CHINESE, "操作"),
    FIELD_OPERATION_2("FIELD_OPERATION", TranslationEnum.ENGLISH, "operation"),

    TITLE_ADD_USER_1("FIELD_ADD_NAME", TranslationEnum.CHINESE, "添加用户"),
    TITLE_ADD_USER_2("FIELD_ADD_NAME", TranslationEnum.ENGLISH, "add user"),

    TITLE_EDIT_USER_1("TITLE_EDIT_USER", TranslationEnum.CHINESE, "编辑用户"),
    TITLE_EDIT_USER_2("TITLE_EDIT_USER", TranslationEnum.ENGLISH, "edit user"),

    TITLE_ROLE_ASSIGN_1("TITLE_ROLE_ASSIGN", TranslationEnum.CHINESE, "角色分配"),
    TITLE_ROLE_ASSIGN_2("TITLE_ROLE_ASSIGN", TranslationEnum.ENGLISH, "role assign"),

    FIELD_DEPT_1("FIELD_DEPT", TranslationEnum.CHINESE, "部门"),
    FIELD_DEPT_2("FIELD_DEPT", TranslationEnum.ENGLISH, "dept"),

    MENU_PAPER_REVIEW_1("MENU_PAPER_REVIEW", TranslationEnum.CHINESE, "论文评审"),
    MENU_PAPER_REVIEW_2("MENU_PAPER_REVIEW", TranslationEnum.ENGLISH, "paper review"),

    MENU_CALL_1("MENU_CALL", TranslationEnum.CHINESE, "论坛主题征集"),
    MENU_CALL_2("MENU_CALL", TranslationEnum.ENGLISH, "Call for theme and subthemes"),


    /*----------------------------------------------------------------*/
    FIELD_ACCOUNT_PHONE_1("FIELD_ACCOUNT_PHONE", TranslationEnum.CHINESE, "账号/手机"),
    FIELD_ACCOUNT_PHONE_2("FIELD_ACCOUNT_PHONE", TranslationEnum.ENGLISH, "Account/Phone"),

    FIELD_ACCOUNTPHONE_1("FIELD_ACCOUNTPHONE", TranslationEnum.CHINESE, "请输入账号/手机"),
    FIELD_ACCOUNTPHONE_2("FIELD_ACCOUNTPHONE", TranslationEnum.ENGLISH, "Please enter account/Phone"),

    FIELD_Background_1("FIELD_Background", TranslationEnum.CHINESE, "后台管理"),
    FIELD_Background_2("FIELD_Background", TranslationEnum.ENGLISH, "Background management"),

    FIELD_LogInRegister_1("FIELD_LogInRegister", TranslationEnum.CHINESE, "登录/注册"),
    FIELD_LogInRegister_2("FIELD_LogInRegister", TranslationEnum.ENGLISH, "Log in/Register"),

    FIELD_LogIn_1("FIELD_LogIn", TranslationEnum.CHINESE, "用户登录"),
    FIELD_LogIn_2("FIELD_LogIn", TranslationEnum.ENGLISH, "Log in"),

    FIELD_LogInButton_1("FIELD_LogInButton", TranslationEnum.CHINESE, "登录"),
    FIELD_LogInButton_2("FIELD_LogInButton", TranslationEnum.ENGLISH, "Log in"),

    FIELD_PleaseAccount_1("FIELD_PleaseAccount", TranslationEnum.CHINESE, "请输入登录账号"),
    FIELD_PleaseAccount_2("FIELD_PleaseAccount", TranslationEnum.ENGLISH, "account"),

    FIELD_PleasePassword_1("FIELD_PleasePassword", TranslationEnum.CHINESE, "请输入登录密码"),
    FIELD_PleasePassword_2("FIELD_PleasePassword", TranslationEnum.ENGLISH, "password"),

    FIELD_PleaseRepeatPassword_1("FIELD_PleaseRepeatPassword", TranslationEnum.CHINESE, "请输入登录密码"),
    FIELD_PleaseRepeatPassword_2("FIELD_PleaseRepeatPassword", TranslationEnum.ENGLISH, "repeat password"),

    FIELD_PleaseCode_1("FIELD_PleaseCode", TranslationEnum.CHINESE, "请输入验证码"),
    FIELD_PleaseCode_2("FIELD_PleaseCode", TranslationEnum.ENGLISH, "SMS verification code"),

    FIELD_SMS_1("FIELD_SMS", TranslationEnum.CHINESE, "验证码"),
    FIELD_SMS_2("FIELD_SMS", TranslationEnum.ENGLISH, "SMS Verification Code"),

    FIELD_ScanQRCode_1("FIELD_ScanQRCode", TranslationEnum.CHINESE, "扫码登录"),
    FIELD_ScanQRCode_2("FIELD_ScanQRCode", TranslationEnum.ENGLISH, "Scan QR Code to Log in"),

    FIELD_ForgotPassword_1("FIELD_ForgotPassword", TranslationEnum.CHINESE, "忘记密码"),
    FIELD_ForgotPassword_2("FIELD_ForgotPassword", TranslationEnum.ENGLISH, "Forgot Password"),

    FIELD_LogSMS_1("FIELD_LogSMS", TranslationEnum.CHINESE, "手机验证码登录"),
    FIELD_LogSMS_2("FIELD_LogSMS", TranslationEnum.ENGLISH, "Log in with SMS Verification Code"),

    FIELD_Signupnow_1("FIELD_Signupnow", TranslationEnum.CHINESE, "立即注册"),
    FIELD_Signupnow_2("FIELD_Signupnow", TranslationEnum.ENGLISH, "Sign up now"),

    FIELD_ReturnLog_1("FIELD_ReturnLog", TranslationEnum.CHINESE, "返回账号登录"),
    FIELD_ReturnLog_2("FIELD_ReturnLog", TranslationEnum.ENGLISH, "Return to your account and log in"),

    FIELD_Registration_1("FIELD_Registration", TranslationEnum.CHINESE, "用户注册"),
    FIELD_Registration_2("FIELD_Registration", TranslationEnum.ENGLISH, "User Registration"),

    FIELD_Alreadyhave_1("FIELD_Alreadyhave", TranslationEnum.CHINESE, "已有账号"),
    FIELD_Alreadyhave_2("FIELD_Alreadyhave", TranslationEnum.ENGLISH, "Already have an account"),

    FIELD_ReceiveSMS_1("FIELD_ReceiveSMS", TranslationEnum.CHINESE, "获取短信验证码"),
    FIELD_ReceiveSMS_2("FIELD_ReceiveSMS", TranslationEnum.ENGLISH, "Receive SMS Verification Code"),

    FIELD_Authentication_1("FIELD_Authentication", TranslationEnum.CHINESE, "验证身份"),
    FIELD_Authentication_2("FIELD_Authentication", TranslationEnum.ENGLISH, "Authentication"),

    FIELD_NextStep_1("FIELD_NextStep", TranslationEnum.CHINESE, "下一步"),
    FIELD_NextStep_2("FIELD_NextStep", TranslationEnum.ENGLISH, "Next Step"),

    FIELD_SetANewPassword_1("FIELD_SetANewPassword", TranslationEnum.CHINESE, "设置新密码"),
    FIELD_SetANewPassword_2("FIELD_SetANewPassword", TranslationEnum.ENGLISH, "Set a new password"),

    FIELD_Done_1("FIELD_Done", TranslationEnum.CHINESE, "完成"),
    FIELD_Done_2("FIELD_Done", TranslationEnum.ENGLISH, "Done"),

    FIELD_Home_1("FIELD_Home", TranslationEnum.CHINESE, "首页"),
    FIELD_Home_2("FIELD_Home", TranslationEnum.ENGLISH, "Home"),

    FIELD_Personalcenter_1("FIELD_Personalcenter", TranslationEnum.CHINESE, "个人中心"),
    FIELD_Personalcenter_2("FIELD_Personalcenter", TranslationEnum.ENGLISH, "Personal center"),

    FIELD_PersonalInformation_1("FIELD_PersonalInformation", TranslationEnum.CHINESE, "个人信息"),
    FIELD_PersonalInformation_2("FIELD_PersonalInformation", TranslationEnum.ENGLISH, "Personal Information"),

    FIELD_ConferenceManual_1("FIELD_ConferenceManual", TranslationEnum.CHINESE, "会议手册"),
    FIELD_ConferenceManual_2("FIELD_ConferenceManual", TranslationEnum.ENGLISH, "Conference Manual"),

    FIELD_ConferencePayment_1("FIELD_ConferencePayment", TranslationEnum.CHINESE, "会议缴费"),
    FIELD_ConferencePayment_2("FIELD_ConferencePayment", TranslationEnum.ENGLISH, "Conference Payment"),

    FIELD_Forum_1("FIELD_Forum", TranslationEnum.CHINESE, "论坛"),
    FIELD_Forum_2("FIELD_Forum", TranslationEnum.ENGLISH, "Forum"),

    FIELD_Seat_1("FIELD_Seat", TranslationEnum.CHINESE, "座位"),
    FIELD_Seat_2("FIELD_Seat", TranslationEnum.ENGLISH, "Seat"),

    FIELD_MySeat_1("FIELD_MySeat", TranslationEnum.CHINESE, "我的座位"),
    FIELD_MySeat_2("FIELD_MySeat", TranslationEnum.ENGLISH, "My Seat"),

    FIELD_SeatRow_1("FIELD_SeatRow", TranslationEnum.CHINESE, "排"),
    FIELD_SeatRow_2("FIELD_SeatRow", TranslationEnum.ENGLISH, "row"),

    FIELD_SeatCol_1("FIELD_SeatCol", TranslationEnum.CHINESE, "号"),
    FIELD_SeatCol_2("FIELD_SeatCol", TranslationEnum.ENGLISH, "col"),

    FIELD_ForumRegistration_1("FIELD_ForumRegistration", TranslationEnum.CHINESE, "论坛报名"),
    FIELD_ForumRegistration_2("FIELD_ForumRegistration", TranslationEnum.ENGLISH, "Forum Registration"),

    FIELD_DocumentsDownload_1("FIELD_DocumentsDownload", TranslationEnum.CHINESE, "材料下载"),
    FIELD_DocumentsDownload_2("FIELD_DocumentsDownload", TranslationEnum.ENGLISH, "Documents Download"),

    FIELD_NameDocuments_1("FIELD_NameDocuments", TranslationEnum.CHINESE, "材料名称"),
    FIELD_NameDocuments_2("FIELD_NameDocuments", TranslationEnum.ENGLISH, "Name of Documents"),

    FIELD_ConferenceInformation_1("FIELD_ConferenceInformation", TranslationEnum.CHINESE, "会议信息"),
    FIELD_ConferenceInformation_2("FIELD_ConferenceInformation", TranslationEnum.ENGLISH, "Conference Information"),

    FIELD_DescriptionConference_1("FIELD_DescriptionConference", TranslationEnum.CHINESE, "会议描述"),
    FIELD_DescriptionConference_2("FIELD_DescriptionConference", TranslationEnum.ENGLISH, "Description of Conference"),

    FIELD_ConferenceDate_1("FIELD_ConferenceDate", TranslationEnum.CHINESE, "会议时间"),
    FIELD_ConferenceDate_2("FIELD_ConferenceDate", TranslationEnum.ENGLISH, "Conference Date"),

    FIELD_ConferencePlace_1("FIELD_ConferencePlace", TranslationEnum.CHINESE, "会议地点"),
    FIELD_ConferencePlace_2("FIELD_ConferencePlace", TranslationEnum.ENGLISH, "Conference place"),

    FIELD_RegistrationDeadline_1("FIELD_RegistrationDeadline", TranslationEnum.CHINESE, "报名时间"),
    FIELD_RegistrationDeadline_2("FIELD_RegistrationDeadline", TranslationEnum.ENGLISH, "Registration Deadline"),

    FIELD_ForumInformation_1("FIELD_ForumInformation", TranslationEnum.CHINESE, "论坛信息"),
    FIELD_ForumInformation_2("FIELD_ForumInformation", TranslationEnum.ENGLISH, "Forum Information"),

    FIELD_VenueForum_1("FIELD_VenueForum", TranslationEnum.CHINESE, "论坛地点"),
    FIELD_VenueForum_2("FIELD_VenueForum", TranslationEnum.ENGLISH, "Venue of Forum"),

    FIELD_DateForum_1("FIELD_DateForum", TranslationEnum.CHINESE, "论坛时间"),
    FIELD_DateForum_2("FIELD_DateForum", TranslationEnum.ENGLISH, "Date of Forum"),

    FIELD_MySubmission_1("FIELD_MySubmission", TranslationEnum.CHINESE, "我的论文"),
    FIELD_MySubmission_2("FIELD_MySubmission", TranslationEnum.ENGLISH, "My Submission"),

    FIELD_Author_1("FIELD_Author", TranslationEnum.CHINESE, "作者"),
    FIELD_Author_2("FIELD_Author", TranslationEnum.ENGLISH, "Author"),

    FIELD_Abstract_1("FIELD_Abstract", TranslationEnum.CHINESE, "摘要"),
    FIELD_Abstract_2("FIELD_Abstract", TranslationEnum.ENGLISH, "Abstract"),

    FIELD_ReviewStatus_1("FIELD_ReviewStatus", TranslationEnum.CHINESE, "论文评审状态"),
    FIELD_ReviewStatus_2("FIELD_ReviewStatus", TranslationEnum.ENGLISH, "Review Status"),

    FIELD_ReviewingReport_1("FIELD_ReviewingReport", TranslationEnum.CHINESE, "评审报告"),
    FIELD_ReviewingReport_2("FIELD_ReviewingReport", TranslationEnum.ENGLISH, "Reviewing Report"),

    FIELD_BasicInformation_1("FIELD_BasicInformation", TranslationEnum.CHINESE, "基本信息"),
    FIELD_BasicInformation_2("FIELD_BasicInformation", TranslationEnum.ENGLISH, "Basic information"),

    FIELD_ACCOUNT_1("FIELD_ACCOUNT", TranslationEnum.CHINESE, "账号"),
    FIELD_ACCOUNT_2("FIELD_ACCOUNT", TranslationEnum.ENGLISH, "Account"),

    FIELD_Password_1("FIELD_Password", TranslationEnum.CHINESE, "密码"),
    FIELD_Password_2("FIELD_Password", TranslationEnum.ENGLISH, "Password"),

    FIELD_RepeatPassword_1("FIELD_RepeatPassword", TranslationEnum.CHINESE, "重复密码"),
    FIELD_RepeatPassword_2("FIELD_RepeatPassword", TranslationEnum.ENGLISH, "Repeat password"),

    FIELD_ChangePassword_1("FIELD_ChangePassword", TranslationEnum.CHINESE, "修改密码"),
    FIELD_ChangePassword_2("FIELD_ChangePassword", TranslationEnum.ENGLISH, "Change password"),

    FIELD_WeChat_1("FIELD_WeChat", TranslationEnum.CHINESE, "微信"),
    FIELD_WeChat_2("FIELD_WeChat", TranslationEnum.ENGLISH, "WeChat"),

    FIELD_Binding_1("FIELD_Binding", TranslationEnum.CHINESE, "绑定"),
    FIELD_Binding_2("FIELD_Binding", TranslationEnum.ENGLISH, "Binding"),

    FIELD_Unbinding_1("FIELD_Unbinding", TranslationEnum.CHINESE, "解绑"),
    FIELD_Unbinding_2("FIELD_Unbinding", TranslationEnum.ENGLISH, "Unbinding"),

    FIELD_Gender_1("FIELD_Gender", TranslationEnum.CHINESE, "性别"),
    FIELD_Gender_2("FIELD_Gender", TranslationEnum.ENGLISH, "Gender"),

    FIELD_Email_1("FIELD_Email", TranslationEnum.CHINESE, "邮箱"),
    FIELD_Email_2("FIELD_Email", TranslationEnum.ENGLISH, "Email"),

    FIELD_NAME_1("FIELD_NAME", TranslationEnum.CHINESE, "姓名"),
    FIELD_NAME_2("FIELD_NAME", TranslationEnum.ENGLISH, "Name"),

    FIELD_DateBirth_1("FIELD_DateBirth", TranslationEnum.CHINESE, "生日"),
    FIELD_DateBirth_2("FIELD_DateBirth", TranslationEnum.ENGLISH, "Date of Birth"),

    FIELD_Organization_1("FIELD_Organization", TranslationEnum.CHINESE, "工作单位"),
    FIELD_Organization_2("FIELD_Organization", TranslationEnum.ENGLISH, "Organization"),

    FIELD_Title_1("FIELD_Title", TranslationEnum.CHINESE, "职称"),
    FIELD_Title_2("FIELD_Title", TranslationEnum.ENGLISH, "Title"),

    FIELD_None_1("FIELD_None", TranslationEnum.CHINESE, "无"),
    FIELD_None_2("FIELD_None", TranslationEnum.ENGLISH, "None"),

    FIELD_RATA_1("FIELD_RATA", TranslationEnum.CHINESE, "初级"),
    FIELD_RATA_2("FIELD_RATA", TranslationEnum.ENGLISH, "Research Assistant/Teaching Assistant"),

    FIELD_AP_1("FIELD_AP", TranslationEnum.CHINESE, "中级"),
    FIELD_AP_2("FIELD_AP", TranslationEnum.ENGLISH, "Assistant Professor"),

    FIELD_PAP_1("FIELD_PAP", TranslationEnum.CHINESE, "高级"),
    FIELD_PAP_2("FIELD_PAP", TranslationEnum.ENGLISH, "Professor/Associate Professor"),

    FIELD_EducationBackground_1("FIELD_EducationBackground", TranslationEnum.CHINESE, "学历"),
    FIELD_EducationBackground_2("FIELD_EducationBackground", TranslationEnum.ENGLISH, "Education Background"),

    FIELD_CollegeDegree_1("FIELD_CollegeDegree", TranslationEnum.CHINESE, "专科"),
    FIELD_CollegeDegree_2("FIELD_CollegeDegree", TranslationEnum.ENGLISH, "College Degree"),

    FIELD_BachelorDegree_1("FIELD_BachelorDegree", TranslationEnum.CHINESE, "本科"),
    FIELD_BachelorDegree_2("FIELD_BachelorDegree", TranslationEnum.ENGLISH, "Bachelor Degree"),

    FIELD_MasterDegree_1("FIELD_MasterDegree", TranslationEnum.CHINESE, "硕士"),
    FIELD_MasterDegree_2("FIELD_MasterDegree", TranslationEnum.ENGLISH, "Master's Degree"),

    FIELD_DoctoralDegree_1("FIELD_DoctoralDegree", TranslationEnum.CHINESE, "博士"),
    FIELD_DoctoralDegree_2("FIELD_DoctoralDegree", TranslationEnum.ENGLISH, "Doctoral Degree/Ph.D."),

    FIELD_Position_1("FIELD_Position", TranslationEnum.CHINESE, "职务"),
    FIELD_Position_2("FIELD_Position", TranslationEnum.ENGLISH, "Position"),

    FIELD_ResearchFields_1("FIELD_ResearchFields", TranslationEnum.CHINESE, "研究方向"),
    FIELD_ResearchFields_2("FIELD_ResearchFields", TranslationEnum.ENGLISH, "Research Fields"),

    FIELD_Mobilephone_1("FIELD_Mobilephone", TranslationEnum.CHINESE, "手机号"),
    FIELD_Mobilephone_2("FIELD_Mobilephone", TranslationEnum.ENGLISH, "Mobilephone Number"),

    FIELD_CBMN_1("FIELD_CBMN", TranslationEnum.CHINESE, "修改绑定手机号"),
    FIELD_CBMN_2("FIELD_CBMN", TranslationEnum.ENGLISH, "Change Bound Mobilephone Number"),

    FIELD_Save_1("FIELD_Save", TranslationEnum.CHINESE, "保存"),
    FIELD_Save_2("FIELD_Save", TranslationEnum.ENGLISH, "Save"),

    FIELD_ParticipantsInfo_1("FIELD_ParticipantsInfo", TranslationEnum.CHINESE, "嘉宾信息"),
    FIELD_ParticipantsInfo_2("FIELD_ParticipantsInfo", TranslationEnum.ENGLISH, "Participants Information"),

    FIELD_UPPS_1("FIELD_UPPS", TranslationEnum.CHINESE, "上传发言稿/PPT"),
    FIELD_UPPS_2("FIELD_UPPS", TranslationEnum.ENGLISH, "Upload Presentation/PowerPoint Slides"),

    FIELD_UPS_1("FIELD_UPS", TranslationEnum.CHINESE, "上传PPT"),
    FIELD_UPS_2("FIELD_UPS", TranslationEnum.ENGLISH, "Upload PowerPoint Slides"),

    FIELD_UP_1("FIELD_UP", TranslationEnum.CHINESE, "上传发言稿"),
    FIELD_UP_2("FIELD_UP", TranslationEnum.ENGLISH, "Upload Presentation"),

    FIELD_IOFP_1("FIELD_IOFP", TranslationEnum.CHINESE, "嘉宾简介"),
    FIELD_IOFP_2("FIELD_IOFP", TranslationEnum.ENGLISH, "Introduction of Participants"),

    FIELD_SelectDocuments_1("FIELD_SelectDocuments", TranslationEnum.CHINESE, "选择文件"),
    FIELD_SelectDocuments_2("FIELD_SelectDocuments", TranslationEnum.ENGLISH, "Select Documents"),

    FIELD_InformationExperts_1("FIELD_InformationExperts", TranslationEnum.CHINESE, "专家信息"),
    FIELD_InformationExperts_2("FIELD_InformationExperts", TranslationEnum.ENGLISH, "Information of Experts"),

    FIELD_IDP_1("FIELD_IDP", TranslationEnum.CHINESE, "证件类型"),
    FIELD_IDP_2("FIELD_IDP", TranslationEnum.ENGLISH, "ID/Passport"),

    FIELD_IDPN_1("FIELD_IDPN", TranslationEnum.CHINESE, "证件号"),
    FIELD_IDPN_2("FIELD_IDPN", TranslationEnum.ENGLISH, "ID/Passport Number"),

    FIELD_POBA_1("FIELD_POBA", TranslationEnum.CHINESE, "开户省份"),
    FIELD_POBA_2("FIELD_POBA", TranslationEnum.ENGLISH, "Province of Bank Account"),

    FIELD_COBA_1("FIELD_COBA", TranslationEnum.CHINESE, "开户城市"),
    FIELD_COBA_2("FIELD_COBA", TranslationEnum.ENGLISH, "City of Bank Account"),

    FIELD_NameBank_1("FIELD_NameBank", TranslationEnum.CHINESE, "银行机构"),
    FIELD_NameBank_2("FIELD_NameBank", TranslationEnum.ENGLISH, "Name of Bank"),

    FIELD_CNAPS_1("FIELD_CNAPS", TranslationEnum.CHINESE, "联行号"),
    FIELD_CNAPS_2("FIELD_CNAPS", TranslationEnum.ENGLISH, "CNAPS"),

    FIELD_BankDeposit_1("FIELD_BankDeposit", TranslationEnum.CHINESE, "开户行"),
    FIELD_BankDeposit_2("FIELD_BankDeposit", TranslationEnum.ENGLISH, "Bank of Deposit"),

    FIELD_AccountNumber_1("FIELD_AccountNumber", TranslationEnum.CHINESE, "个人账号"),
    FIELD_AccountNumber_2("FIELD_AccountNumber", TranslationEnum.ENGLISH, "AccountNumber"),

    FIELD_MyConference_1("FIELD_MyConference", TranslationEnum.CHINESE, "我的会议"),
    FIELD_MyConference_2("FIELD_MyConference", TranslationEnum.ENGLISH, "My Conference"),

    FIELD_MeetRegistration_1("FIELD_MeetRegistration", TranslationEnum.CHINESE, "会议注册"),
    FIELD_MeetRegistration_2("FIELD_MeetRegistration", TranslationEnum.ENGLISH, "Registration"),

    FIELD_Search_1("FIELD_Search", TranslationEnum.CHINESE, "搜索"),
    FIELD_Search_2("FIELD_Search", TranslationEnum.ENGLISH, "Search"),

    FIELD_ConferenceName_1("FIELD_ConferenceName", TranslationEnum.CHINESE, "会议名称"),
    FIELD_ConferenceName_2("FIELD_ConferenceName", TranslationEnum.ENGLISH, "Conference Name"),

    FIELD_NameParticipants_1("FIELD_NameParticipants", TranslationEnum.CHINESE, "参会人姓名"),
    FIELD_NameParticipants_2("FIELD_NameParticipants", TranslationEnum.ENGLISH, "Name of Participants"),

    FIELD_PSTC_1("FIELD_PSTC", TranslationEnum.CHINESE, "参会论文"),
    FIELD_PSTC_2("FIELD_PSTC", TranslationEnum.ENGLISH, "Paper Submitted to Conference"),

    FIELD_ConferenceStatus_1("FIELD_ConferenceStatus", TranslationEnum.CHINESE, "会议状态"),
    FIELD_ConferenceStatus_2("FIELD_ConferenceStatus", TranslationEnum.ENGLISH, "Conference Status"),

    FIELD_ApplicationStatus_1("FIELD_ApplicationStatus", TranslationEnum.CHINESE, "申请状态"),
    FIELD_ApplicationStatus_2("FIELD_ApplicationStatus", TranslationEnum.ENGLISH, "Application Status"),

    FIELD_Operate_1("FIELD_Operate", TranslationEnum.CHINESE, "操作"),
    FIELD_Operate_2("FIELD_Operate", TranslationEnum.ENGLISH, "Operate"),

    FIELD_PapaerDetails_1("FIELD_PapaerDetails", TranslationEnum.CHINESE, "论文详情"),
    FIELD_PapaerDetails_2("FIELD_PapaerDetails", TranslationEnum.ENGLISH, "Papaer Details"),

    FIELD_Revise_1("FIELD_Revise", TranslationEnum.CHINESE, "修改"),
    FIELD_Revise_2("FIELD_Revise", TranslationEnum.ENGLISH, "Revise"),

    FIELD_Delete_1("FIELD_Delete", TranslationEnum.CHINESE, "删除"),
    FIELD_Delete_2("FIELD_Delete", TranslationEnum.ENGLISH, "Delete"),

    FIELD_ApplicationCancelled_1("FIELD_ApplicationCancelled", TranslationEnum.CHINESE, "取消申请"),
    FIELD_ApplicationCancelled_2("FIELD_ApplicationCancelled", TranslationEnum.ENGLISH, "Application Cancelled"),

    FIELD_Pay_1("FIELD_Pay", TranslationEnum.CHINESE, "缴费"),
    FIELD_Pay_2("FIELD_Pay", TranslationEnum.ENGLISH, "Pay"),

    FIELD_SelectConference_1("FIELD_SelectConference", TranslationEnum.CHINESE, "选择论坛"),
    FIELD_SelectConference_2("FIELD_SelectConference", TranslationEnum.ENGLISH, "Select Conference"),

    FIELD_SeeConference_1("FIELD_SeeConference", TranslationEnum.CHINESE, "查看论坛"),
    FIELD_SeeConference_2("FIELD_SeeConference", TranslationEnum.ENGLISH, "See the Conference"),

    FIELD_AFAI_1("FIELD_AFAI", TranslationEnum.CHINESE, "申请开票"),
    FIELD_AFAI_2("FIELD_AFAI", TranslationEnum.ENGLISH, "Apply for an invoice"),

    FIELD_SIOI_1("FIELD_SIOI", TranslationEnum.CHINESE, "查看开票"),
    FIELD_SIOI_2("FIELD_SIOI", TranslationEnum.ENGLISH, "See information of invoice"),

    FIELD_CancelRegistration_1("FIELD_CancelRegistration", TranslationEnum.CHINESE, "取消参会"),
    FIELD_CancelRegistration_2("FIELD_CancelRegistration", TranslationEnum.ENGLISH, "Cancel Registration"),

    FIELD_Nationality_1("FIELD_Nationality", TranslationEnum.CHINESE, "所属国家"),
    FIELD_Nationality_2("FIELD_Nationality", TranslationEnum.ENGLISH, "Nationality"),

    FIELD_Province_1("FIELD_Province", TranslationEnum.CHINESE, "所属省份"),
    FIELD_Province_2("FIELD_Province", TranslationEnum.ENGLISH, "Province"),

    FIELD_City_1("FIELD_City", TranslationEnum.CHINESE, "所属城市"),
    FIELD_City_2("FIELD_City", TranslationEnum.ENGLISH, "City"),

    FIELD_ChineseTitle_1("FIELD_ChineseTitle", TranslationEnum.CHINESE, "中文题目"),
    FIELD_ChineseTitle_2("FIELD_ChineseTitle", TranslationEnum.ENGLISH, "Chinese Title"),

    FIELD_EnglishTitle_1("FIELD_EnglishTitle", TranslationEnum.CHINESE, "英文题目"),
    FIELD_EnglishTitle_2("FIELD_EnglishTitle", TranslationEnum.ENGLISH, "English Title"),

    FIELD_KWIC_1("FIELD_KWIC", TranslationEnum.CHINESE, "中文关键词"),
    FIELD_KWIC_2("FIELD_KWIC", TranslationEnum.ENGLISH, "Key words in Chinese"),

    FIELD_KWIE_1("FIELD_KWIE", TranslationEnum.CHINESE, "英文关键词"),
    FIELD_KWIE_2("FIELD_KWIE", TranslationEnum.ENGLISH, "Key words in English"),

    FIELD_AIC_1("FIELD_AIC", TranslationEnum.CHINESE, "中文摘要"),
    FIELD_AIC_2("FIELD_AIC", TranslationEnum.ENGLISH, "Abstract in Chinese"),

    FIELD_AIE_1("FIELD_AIE", TranslationEnum.CHINESE, "英文摘要"),
    FIELD_AIE_2("FIELD_AIE", TranslationEnum.ENGLISH, "Abstract in English"),

    FIELD_RAOP_1("FIELD_RAOP", TranslationEnum.CHINESE, "论文领域"),
    FIELD_RAOP_2("FIELD_RAOP", TranslationEnum.ENGLISH, "Research Area of Paper"),

    FIELD_PDFAttachment_1("FIELD_PDFAttachment", TranslationEnum.CHINESE, "论文PDF附件"),
    FIELD_PDFAttachment_2("FIELD_PDFAttachment", TranslationEnum.ENGLISH, "PDF Attachment"),

    FIELD_WordAttachment_1("FIELD_WordAttachment", TranslationEnum.CHINESE, "论文Word附件"),
    FIELD_WordAttachment_2("FIELD_WordAttachment", TranslationEnum.ENGLISH, "Word Attachment"),

    FIELD_Submit_1("FIELD_Submit", TranslationEnum.CHINESE, "提交"),
    FIELD_Submit_2("FIELD_Submit", TranslationEnum.ENGLISH, "Submit"),

    FIELD_RACWTCOF_1("FIELD_RACWTCOF", TranslationEnum.CHINESE, "阅读并同意遵守《论坛章程》"),
    FIELD_RACWTCOF_2("FIELD_RACWTCOF", TranslationEnum.ENGLISH, "Read and comply with the Charter of Forum"),

    FIELD_MyReview_1("FIELD_MyReview", TranslationEnum.CHINESE, "我的评审"),
    FIELD_MyReview_2("FIELD_MyReview", TranslationEnum.ENGLISH, "My Review"),

    FIELD_PaperReview_1("FIELD_PaperReview", TranslationEnum.CHINESE, "论文评审"),
    FIELD_PaperReview_2("FIELD_PaperReview", TranslationEnum.ENGLISH, "Paper Review"),

    FIELD_ROERE_1("FIELD_ROERE", TranslationEnum.CHINESE, "教改实验评审"),
    FIELD_ROERE_2("FIELD_ROERE", TranslationEnum.ENGLISH, "Review of Educational Reform Experiments"),

    FIELD_ROOP_1("FIELD_ROOP", TranslationEnum.CHINESE, "优秀论著评审"),
    FIELD_ROOP_2("FIELD_ROOP", TranslationEnum.ENGLISH, "Review of Outstanding Paper"),

    FIELD_InitialReview_1("FIELD_InitialReview", TranslationEnum.CHINESE, "专家初评"),
    FIELD_InitialReview_2("FIELD_InitialReview", TranslationEnum.ENGLISH, "Initial Review"),

    FIELD_FurtherReview_1("FIELD_FurtherReview", TranslationEnum.CHINESE, "专家复评"),
    FIELD_FurtherReview_2("FIELD_FurtherReview", TranslationEnum.ENGLISH, "Further Review"),

    FIELD_TitlePaper_1("FIELD_TitlePaper", TranslationEnum.CHINESE, "论文题目"),
    FIELD_TitlePaper_2("FIELD_TitlePaper", TranslationEnum.ENGLISH, "Title of Paper"),

    FIELD_AuthorPaper_1("FIELD_AuthorPaper", TranslationEnum.CHINESE, "论文作者"),
    FIELD_AuthorPaper_2("FIELD_AuthorPaper", TranslationEnum.ENGLISH, "Author of Paper"),

    FIELD_SOIR_1("FIELD_SOIR", TranslationEnum.CHINESE, "初评分数"),
    FIELD_SOIR_2("FIELD_SOIR", TranslationEnum.ENGLISH, "Score of Initial Review"),

    FIELD_OOIR_1("FIELD_OOIR", TranslationEnum.CHINESE, "初评结果"),
    FIELD_OOIR_2("FIELD_OOIR", TranslationEnum.ENGLISH, "Outcome of Initial Review"),

    FIELD_RecommendOrNot_1("FIELD_RecommendOrNot", TranslationEnum.CHINESE, "是否推优"),
    FIELD_RecommendOrNot_2("FIELD_RecommendOrNot", TranslationEnum.ENGLISH, "Recommend or not"),

    FIELD_Reviewstatus_1("FIELD_Reviewstatus", TranslationEnum.CHINESE, "评审状态"),
    FIELD_Reviewstatus_2("FIELD_Reviewstatus", TranslationEnum.ENGLISH, "Review status"),

    FIELD_TitleDeliverables_1("FIELD_TitleDeliverables", TranslationEnum.CHINESE, "成果名称"),
    FIELD_TitleDeliverables_2("FIELD_TitleDeliverables", TranslationEnum.ENGLISH, "Title of Deliverables"),

    FIELD_NameApplicant_1("FIELD_NameApplicant", TranslationEnum.CHINESE, "申请人姓名"),
    FIELD_NameApplicant_2("FIELD_NameApplicant", TranslationEnum.ENGLISH, "Name of Applicant"),

    FIELD_ReviewExperts_1("FIELD_ReviewExperts", TranslationEnum.CHINESE, "评审专家"),
    FIELD_ReviewExperts_2("FIELD_ReviewExperts", TranslationEnum.ENGLISH, "Review experts"),

    FIELD_SeeDetails_1("FIELD_SeeDetails", TranslationEnum.CHINESE, "查看详情"),
    FIELD_SeeDetails_2("FIELD_SeeDetails", TranslationEnum.ENGLISH, "See Details"),

    FIELD_ReviewComment_1("FIELD_ReviewComment", TranslationEnum.CHINESE, "评审意见"),
    FIELD_ReviewComment_2("FIELD_ReviewComment", TranslationEnum.ENGLISH, "Review comment"),

    FIELD_AttachmentDownload_1("FIELD_AttachmentDownload", TranslationEnum.CHINESE, "下载附件"),
    FIELD_AttachmentDownload_2("FIELD_AttachmentDownload", TranslationEnum.ENGLISH, "Attachment Download"),

    FIELD_Download_1("FIELD_Download", TranslationEnum.CHINESE, "下载"),
    FIELD_Download_2("FIELD_Download", TranslationEnum.ENGLISH, "Download"),

    FIELD_PreviewOnline_1("FIELD_PreviewOnline", TranslationEnum.CHINESE, "在线预览"),
    FIELD_PreviewOnline_2("FIELD_PreviewOnline", TranslationEnum.ENGLISH, "Preview Online"),

    FIELD_MAOD_1("FIELD_MAOD", TranslationEnum.CHINESE, "我的成果申请"),
    FIELD_MAOD_2("FIELD_MAOD", TranslationEnum.ENGLISH, "My application of deliverables"),

    FIELD_DeclarationWorks_1("FIELD_DeclarationWorks", TranslationEnum.CHINESE, "著作申报"),
    FIELD_DeclarationWorks_2("FIELD_DeclarationWorks", TranslationEnum.ENGLISH, "Declaration of Works"),

    FIELD_AOERE_1("FIELD_AOERE", TranslationEnum.CHINESE, "教改实验申报"),
    FIELD_AOERE_2("FIELD_AOERE", TranslationEnum.ENGLISH, "Application of Educational Reform Experiments"),

    FIELD_COD_1("FIELD_COD", TranslationEnum.CHINESE, "成果申报类别"),
    FIELD_COD_2("FIELD_COD", TranslationEnum.ENGLISH, "Categorization of Declaration"),

    FIELD_SpecialisedArea_1("FIELD_SpecialisedArea", TranslationEnum.CHINESE, "所属领域"),
    FIELD_SpecialisedArea_2("FIELD_SpecialisedArea", TranslationEnum.ENGLISH, "Specialised Area"),

    FIELD_TypeDeclaration_1("FIELD_TypeDeclaration", TranslationEnum.CHINESE, "成果申报类型"),
    FIELD_TypeDeclaration_2("FIELD_TypeDeclaration", TranslationEnum.ENGLISH, "Type of Declaration"),

    FIELD_ApplyIndividual_1("FIELD_ApplyIndividual", TranslationEnum.CHINESE, "个人申报"),
    FIELD_ApplyIndividual_2("FIELD_ApplyIndividual", TranslationEnum.ENGLISH, "Apply by Individual"),

    FIELD_ApplyOrganization_1("FIELD_ApplyOrganization", TranslationEnum.CHINESE, "单位申报"),
    FIELD_ApplyOrganization_2("FIELD_ApplyOrganization", TranslationEnum.ENGLISH, "Apply by Organization"),

    FIELD_TNOA_1("FIELD_TNOA", TranslationEnum.CHINESE, "申请人手机号 "),
    FIELD_TNOA_2("FIELD_TNOA", TranslationEnum.ENGLISH, "Telephone Number of Applicant"),

    FIELD_EmailApplicant_1("FIELD_EmailApplicant", TranslationEnum.CHINESE, "申请人邮箱"),
    FIELD_EmailApplicant_2("FIELD_EmailApplicant", TranslationEnum.ENGLISH, "Email of Applicant"),

    FIELD_FromOrganization_1("FIELD_FromOrganization", TranslationEnum.CHINESE, "所在单位"),
    FIELD_FromOrganization_2("FIELD_FromOrganization", TranslationEnum.ENGLISH, "Organization"),

    FIELD_PersonCharge_1("FIELD_PersonCharge", TranslationEnum.CHINESE, "负责人"),
    FIELD_PersonCharge_2("FIELD_PersonCharge", TranslationEnum.ENGLISH, "Person in Charge"),

    FIELD_TNOPIC_1("FIELD_TNOPIC", TranslationEnum.CHINESE, "负责人手机号"),
    FIELD_TNOPIC_2("FIELD_TNOPIC", TranslationEnum.ENGLISH, "Telephone Number of Person in Charge"),

    FIELD_NameOrganization_1("FIELD_NameOrganization", TranslationEnum.CHINESE, "单位名称"),
    FIELD_NameOrganization_2("FIELD_NameOrganization", TranslationEnum.ENGLISH, "Name of Organization"),

    FIELD_TitlePosition_1("FIELD_TitlePosition", TranslationEnum.CHINESE, "申请人职称/职务"),
    FIELD_TitlePosition_2("FIELD_TitlePosition", TranslationEnum.ENGLISH, "Title/Position"),

    FIELD_ResearchFieldsApply_1("FIELD_ResearchFieldsApply", TranslationEnum.CHINESE, "申请人研究方向"),
    FIELD_ResearchFieldsApply_2("FIELD_ResearchFieldsApply", TranslationEnum.ENGLISH, "Research Fields"),

    FIELD_AOIFOD_1("FIELD_AOIFOD", TranslationEnum.CHINESE, "优秀成果简介附件"),
    FIELD_AOIFOD_2("FIELD_AOIFOD", TranslationEnum.ENGLISH, "Attachment of Introduction for Outstanding Deliverables"),

    FIELD_AORL_1("FIELD_AORL", TranslationEnum.CHINESE, "专家推荐信附件 "),
    FIELD_AORL_2("FIELD_AORL", TranslationEnum.ENGLISH, "Attachment of Recommendation Letter"),

    FIELD_OriginalityDeclaration_1("FIELD_OriginalityDeclaration", TranslationEnum.CHINESE, "原创承诺书"),
    FIELD_OriginalityDeclaration_2("FIELD_OriginalityDeclaration", TranslationEnum.ENGLISH, "Originality Declaration"),

        FIELD_FormatDownload_1("FIELD_FormatDownload", TranslationEnum.CHINESE, "模板下载"),
    FIELD_FormatDownload_2("FIELD_FormatDownload", TranslationEnum.ENGLISH, "Format Download"),

    FIELD_CallThemes_1("FIELD_CallThemes", TranslationEnum.CHINESE, "我的主题征集"),
    FIELD_CallThemes_2("FIELD_CallThemes", TranslationEnum.ENGLISH, "Call for Themes"),

    FIELD_ThemeConference_1("FIELD_ThemeConference", TranslationEnum.CHINESE, "大会主题"),
    FIELD_ThemeConference_2("FIELD_ThemeConference", TranslationEnum.ENGLISH, "Theme of Conference"),

    FIELD_Rationale_1("FIELD_Rationale", TranslationEnum.CHINESE, "选题意义"),
    FIELD_Rationale_2("FIELD_Rationale", TranslationEnum.ENGLISH, "Rationale"),

    FIELD_ThemeSubforum_1("FIELD_ThemeSubforum", TranslationEnum.CHINESE, "分论坛主题"),
    FIELD_ThemeSubforum_2("FIELD_ThemeSubforum", TranslationEnum.ENGLISH, "Theme of Subforum"),

    FIELD_Informant_1("FIELD_Informant", TranslationEnum.CHINESE, "填报人"),
    FIELD_Informant_2("FIELD_Informant", TranslationEnum.ENGLISH, "Informant"),

    FIELD_MFA_1("FIELD_MFA", TranslationEnum.CHINESE, "我的论坛申报"),
    FIELD_MFA_2("FIELD_MFA", TranslationEnum.ENGLISH, "My Forum Application"),

    FIELD_TitleSubforum_1("FIELD_TitleSubforum", TranslationEnum.CHINESE, "分论坛名称"),
    FIELD_TitleSubforum_2("FIELD_TitleSubforum", TranslationEnum.ENGLISH, "Title of Subforum"),

    FIELD_ScaleSubforum_1("FIELD_ScaleSubforum", TranslationEnum.CHINESE, "分论坛规模"),
    FIELD_ScaleSubforum_2("FIELD_ScaleSubforum", TranslationEnum.ENGLISH, "Scale of Subforum"),

    FIELD_DYAOBOARPT_1("FIELD_DYAOBOARPT", TranslationEnum.CHINESE, "是否有课题团队"),
    FIELD_DYAOBOARPT_2("FIELD_DYAOBOARPT", TranslationEnum.ENGLISH, "Do you apply on behalf of a research project team"),

    FIELD_ApplicationOfStatus_1("FIELD_ApplicationOfStatus", TranslationEnum.CHINESE, "申报状态"),
    FIELD_ApplicationOfStatus_2("FIELD_ApplicationOfStatus", TranslationEnum.ENGLISH, "Application Status"),

    FIELD_NOSF_1("FIELD_NOSF", TranslationEnum.CHINESE, "自设论坛名称"),
    FIELD_NOSF_2("FIELD_NOSF", TranslationEnum.ENGLISH, "Name of Self-organized Forum"),

    FIELD_TOFA_1("FIELD_TOFA", TranslationEnum.CHINESE, "论坛申报类型"),
    FIELD_TOFA_2("FIELD_TOFA", TranslationEnum.ENGLISH, "Type of Forum Application"),

    FIELD_SOSF_1("FIELD_SOSF", TranslationEnum.CHINESE, "自设论坛计划规模"),
    FIELD_SOSF_2("FIELD_SOSF", TranslationEnum.ENGLISH, "Scale of Self-organized Forum"),

    FIELD_AOAP_1("FIELD_AOAP", TranslationEnum.CHINESE, "论坛申报方案附件"),
    FIELD_AOAP_2("FIELD_AOAP", TranslationEnum.ENGLISH, "Attachment of Application Proposal"),

    FIELD_TitleProject_1("FIELD_TitleProject", TranslationEnum.CHINESE, "课题名称"),
    FIELD_TitleProject_2("FIELD_TitleProject", TranslationEnum.ENGLISH, "Title of Project"),

    FIELD_LevelProject_1("FIELD_LevelProject", TranslationEnum.CHINESE, "课题级别"),
    FIELD_LevelProject_2("FIELD_LevelProject", TranslationEnum.ENGLISH, "Level of Project"),

    FIELD_MCI_1("FIELD_MCI", TranslationEnum.CHINESE, "我的合作意向"),
    FIELD_MCI_2("FIELD_MCI", TranslationEnum.ENGLISH, "My Cooperation Intention"),

    FIELD_NOAEO_1("FIELD_NOAEO", TranslationEnum.CHINESE, "企业/单位名称"),
    FIELD_NOAEO_2("FIELD_NOAEO", TranslationEnum.ENGLISH, "Name of Applying Enterprise/Organization"),

    FIELD_USCC_1("FIELD_USCC", TranslationEnum.CHINESE, "统一社会信用代码"),
    FIELD_USCC_2("FIELD_USCC", TranslationEnum.ENGLISH, "Unified Social Credit Code"),

    FIELD_Note_1("FIELD_Note", TranslationEnum.CHINESE, "备注"),
    FIELD_Note_2("FIELD_Note", TranslationEnum.ENGLISH, "Note"),

    FIELD_SFC_1("FIELD_SFC", TranslationEnum.CHINESE, "合作环节 "),
    FIELD_SFC_2("FIELD_SFC", TranslationEnum.ENGLISH, "Session for Cooperation"),

    FIELD_NOMEET_1("FIELD_NOMEET", TranslationEnum.CHINESE, "当前无正在进行的会议。 "),
    FIELD_NOMEET_2("FIELD_NOMEET", TranslationEnum.ENGLISH, "There are currently no ongoing meetings"),

    FIELD_Exit_1("FIELD_Exit", TranslationEnum.CHINESE, "退出"),
    FIELD_Exit_2("FIELD_Exit", TranslationEnum.ENGLISH, "Exit");




    /**
     * 编码
     */
    private String tranCode;

    /**
     * 1:中文  2:英语
     */
    private TranslationEnum translationLanguages;

    /**
     * 翻译的值
     */
    private String tranValue;

    DefaultDicts(String tranCode, TranslationEnum translationLanguages, String tranValue) {
        this.tranCode = tranCode;
        this.translationLanguages = translationLanguages;
        this.tranValue = tranValue;
    }
}
