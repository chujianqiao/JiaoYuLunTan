package cn.stylefeng.guns.meet.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meet.wrapper.MeetWrapper;
import cn.stylefeng.guns.modular.seat.model.params.SeatParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import cn.stylefeng.guns.modular.seat.model.result.SeatResult;
import cn.stylefeng.guns.modular.seat.service.SeatDetailService;
import cn.stylefeng.guns.modular.seat.service.SeatService;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.entity.FileInfo;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.MSOfficeGeneratorUtils;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.guns.util.WordUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chanjar.weixin.mp.bean.device.RespMsg;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 会议表控制器
 *
 * @author wucy
 * @Date 2020-08-05 15:21:03
 */
@Controller
@RequestMapping("/meet")
public class MeetController extends BaseController {

    private String PREFIX = "/meet";

    @Autowired
    private MeetService meetService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatDetailService seatDetailService;

    @Autowired
    private FileInfoService fileInfoService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;


    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/meet.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/meet_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("meetIdParam","");
        return PREFIX + "/meet_edit.html";
    }
    /**
     * 会议手册页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/meetFile")
    public String meetFile(Model model, HttpServletRequest request) {
        MeetParam meetParam = new MeetParam();
        meetParam.setMeetStatus(1);
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        List<Map<String, Object>> list = meets.getRecords();
        model.addAttribute("content", list.get(0).get("content"));
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl", "toPersonCenter");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        return PREFIX + "/meet_file.html";
    }
    /**
     * 会议基本信息页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/info")
    public String info(MeetParam meetParam, HttpServletRequest request) {
        meetParam.setMeetStatus(1);
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        List<Map<String, Object>> list = meets.getRecords();
        Long meetId = Long.parseLong(list.get(0).get("meetId").toString());
        meetParam.setMeetId(meetId);
        request.setAttribute("meetIdParam",meetId);
        return PREFIX + "/meet_edit_admin.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public Object addItem(Meet meet) {
        Long meetId = ToolUtil.getNum19();
        LoginUser user = LoginContextHolder.getContext().getUser();
        meet.setRegUser(user.getId());
        meet.setRegTime(new Date());
        meet.setMeetStatus(0);
        meet.setMeetId(meetId);
        meet.setRealPeoNum(0);
        meet.setRealTheNum(0);
        //同时新增seat表
        SeatParam seatParam = new SeatParam();
        seatParam.setMeetId(meetId);
        seatParam.setMeetType(1L);
        seatParam.setColNum(8);
        seatParam.setRowNum(8);
        seatParam.setSeatType("A");
        this.seatService.add(seatParam);
        this.meetService.save(meet);
        return SUCCESS_TIP;
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(MeetParam meetParam) {
        this.meetService.update(meetParam);
        return ResponseData.success();
    }

    /**
     * 发布接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/pubMeet")
    @ResponseBody
    public ResponseData pubMeet(MeetParam meetParam) {
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        List<Map<String,Object>> list = meets.getRecords();
        //重置会议状态
        for (int i = 0; i < list.size() ; i++) {
            Map map = list.get(i);
            long meetId = Long.parseLong(map.get("meetId").toString());
            MeetParam meetPar = new MeetParam();
            meetPar.setMeetId(meetId);
            meetPar.setMeetStatus(0);
            this.meetService.update(meetPar);
        }
        //修改需要发布的会议的状态
        meetParam.setMeetStatus(1);
        this.meetService.update(meetParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(MeetParam meetParam) {
        this.meetService.delete(meetParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(MeetParam meetParam) {
        Meet detail = this.meetService.getById(meetParam.getMeetId());
        return ResponseData.success(detail);
    }

    /**
     * 查看发布会议详情
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/detailPub")
    @ResponseBody
    public ResponseData detailPub(MeetParam meetParam) {
        Meet detail = this.meetService.getByStatus(1);
        Map map = new HashMap();
        LoginUser user = LoginContextHolder.getContext().getUser();
        SeatDetailResult seatDetailResult = this.seatDetailService.getByUser(user.getId(),detail.getMeetId());
        map.put("detail",detail);
        if (seatDetailResult != null){
            map.put("seat",seatDetailResult);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("beginTime",sdf.format(detail.getBeginTime()));
        map.put("endTime",sdf.format(detail.getEndTime()));
        map.put("joinBegTime",sdf.format(detail.getJoinBegTime()));
        map.put("joinEndTime",sdf.format(detail.getJoinEndTime()));

        SeatParam seatParam = new SeatParam();
        seatParam.setMeetId(detail.getMeetId());
        LayuiPageInfo results = this.seatService.findPageBySpec(seatParam);
        List<SeatResult> list = results.getData();
        SeatResult seatResult = list.get(0);
        long seatId = seatResult.getSeatId();
        map.put("seatId",seatId);

        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-08-05
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(MeetParam meetParam) {
        return this.meetService.findPageBySpec(meetParam);
    }

    /**
     * 查询列表（添加所需的字段）
     * @param meetParam
     * @return
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(MeetParam meetParam){
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        Page wrapped = new MeetWrapper(meets).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 导出word
     * @return
     */
    @RequestMapping(path = "/exportWord")
    public void exportWord(@RequestParam("meetId")String meetIdStr,HttpServletResponse response) {
        Long meetId = Long.parseLong(meetIdStr);
        Meet meet = this.meetService.getById(meetId);
        String content = meet.getContent();
        String title = meet.getMeetName();
        try {
            String str = " <!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val='Cambria Math'/><m:brkBin m:val='before'/><m:brkBinSub m:val='--'/><m:smallFrac m:val='off'/><m:dispDef/><m:lMargin m:val='0'/> <m:rMargin m:val='0'/><m:defJc m:val='centerGroup'/><m:wrapIndent m:val='1440'/><m:intLim m:val='subSup'/><m:naryLim m:val='undOvr'/></m:mathPr></w:WordDocument></xml><![endif]-->";
            String h = " <html xmlns:v='urn:schemas-microsoft-com:vml'xmlns:o='urn:schemas-microsoft-com:office:office'xmlns:w='urn:schemas-microsoft-com:office:word'xmlns:m='http://schemas.microsoft.com/office/2004/12/omml'xmlns='http://www.w3.org/TR/REC-html40'  ";
            content =h+"<head>"+"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+str+"</head><body>"+content+"</body> </html>";
            String dirPath = uploadFolder + "ueditor" + File.separator;
            String wordPath = dirPath + "ueditor.doc";
            File wordFile = new File(wordPath);
            if(!wordFile.getParentFile().exists()){
                wordFile.getParentFile().mkdirs();
            }else{
                wordFile.createNewFile();
            }
            jacob_html2word(content,title,wordPath);

            //下载
            String downName = title + "会议手册.doc";
            FileDownload.fileDownload(response, wordPath, downName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void jacob_html2word(String html,String title,String wordPath){
        try {
            Document document = Jsoup.parse(html);
            String htmlPath = uploadFolder + "ueditor" + File.separator + "ueditor.html";
            // 将html代码写到html文件中
            FileWriter fw = new FileWriter(htmlPath);
            // 写入文件
            fw.write(document.html(), 0, document.html().length());
            fw.flush();
            fw.close();
            // html文件转为word
            writeWordFile(htmlPath,wordPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Html文件转word
     * @param htmlPath
     * @param wordPath
     * @return
     */
    private boolean writeWordFile(String htmlPath,String wordPath) {
        boolean flag = false;
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        try {
            String content = readFile(htmlPath);
            byte b[] = content.getBytes();
            bais = new ByteArrayInputStream(b);
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument(
                    "WordDocument", bais);
            fos = new FileOutputStream(wordPath);
            poifs.writeFilesystem(fos);
            bais.close();
            fos.close();
        } catch (Exception e) {

        }
        return flag;
    }

    private String readFile(String filename) throws Exception {
        StringBuffer buffer = new StringBuffer("");
        BufferedReader br = new BufferedReader(new FileReader(
                new File(filename)));
        try {
            while (br.ready()) {
                buffer.append(br.readLine());
            }
        } catch (Exception e) {

        }
        return buffer.toString();
    }


}


