package cn.stylefeng.guns.modular.demos.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.demos.entity.ExcelItem;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入导出示例
 *
 * @author fengshuonan
 * @Date 2019/3/9 11:03
 */
@Controller
@RequestMapping("/excel")
@Slf4j
public class ExcelController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeetMemberService meetMemberService;

    /**
     * excel导入页面
     *
     * @author fengshuonan
     * @Date 2019/3/9 11:03
     */
    @RequestMapping("/import")
    public String importIndex() {
        return "/demos/excel_import.html";
    }

    /**
     * 上传excel填报
     */
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public ResponseData uploadExcel(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        String name = file.getOriginalFilename();
        request.getSession().setAttribute("upFile", name);
        String fileSavePath = ConstantsContext.getFileUploadPath();
        try {
            file.transferTo(new File(fileSavePath + name));
        } catch (Exception e) {
            log.error("上传那文件出错！", e);
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", IdWorker.getIdStr());
        return ResponseData.success(0, "上传成功", map);
    }

    /**
     * 获取上传成功的数据
     */
    @RequestMapping("/getUploadData")
    @ResponseBody
    public Object getUploadData(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("upFile");
        String fileSavePath = ConstantsContext.getFileUploadPath();
        if (name != null) {
            File file = new File(fileSavePath + name);
            try {
                ImportParams params = new ImportParams();
                params.setTitleRows(1);
                params.setHeadRows(1);
                List result = ExcelImportUtil.importExcel(file, ExcelItem.class, params);

                LayuiPageInfo returns = new LayuiPageInfo();
                returns.setCount(result.size());
                returns.setData(result);
                return returns;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * excel导出
     *
     * @author fengshuonan
     * @Date 2019/3/9 11:03
     */
    @RequestMapping("/export")
    public void export(ModelMap modelMap, HttpServletRequest request,
                       HttpServletResponse response) {

        //初始化表头
        List<ExcelExportEntity> entity = new ArrayList<>();
        entity.add(new ExcelExportEntity("用户id", "user_id"));
        entity.add(new ExcelExportEntity("头像", "avatar"));
        entity.add(new ExcelExportEntity("账号", "account"));
        entity.add(new ExcelExportEntity("姓名", "name"));
        entity.add(new ExcelExportEntity("生日", "birthday"));
        entity.add(new ExcelExportEntity("性别", "sex"));
        entity.add(new ExcelExportEntity("邮箱", "email"));
        entity.add(new ExcelExportEntity("电话", "phone"));
        entity.add(new ExcelExportEntity("角色id", "role_id"));
        entity.add(new ExcelExportEntity("部门id", "dept_id"));
        entity.add(new ExcelExportEntity("状态", "status"));
        entity.add(new ExcelExportEntity("创建时间", "create_time"));

        //初始化化数据
        List<Map<String, Object>> maps = userService.listMaps();
        ArrayList<Map<String, Object>> total = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            total.addAll(maps);
        }

        ExportParams params = new ExportParams("Guns管理系统所有用户", "用户表", ExcelType.XSSF);
        modelMap.put(MapExcelConstants.MAP_LIST, total);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        modelMap.put(MapExcelConstants.FILE_NAME, "Guns管理系统所有用户");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }



    /**
     * excel导出座次表
     *
     * @author fengshuonan
     * @Date 2019/3/9 11:03
     */
    @RequestMapping("/exportSeat")
    public void exportSeat(HttpServletResponse response) {
        try {
            String proPath = System.getProperty("user.dir");
            String parentPath = proPath + File.separator + "template";
            String allPath = parentPath + File.separator + "座次图.xls";

            FileInputStream fs=new FileInputStream(allPath);  //获取d://test.xls
            POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
            HSSFWorkbook wb=new HSSFWorkbook(ps);
            HSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表
            //HSSFRow row=sheet.getRow(5);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值
            //System.out.println(sheet.getLastRowNum()+" "+row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格

            //row.createCell(1).setCellValue(24); //设置第二个（从0开始）单元格的数据

            List<MeetMemberResult> list = meetMemberService.findListBySpec();
            List listUser = new ArrayList();
            for (int i = 0;i < list.size();i++){
                Map userName = userService.getUserInfo(list.get(i).getUserId());
                listUser.add(userName.get("name"));
            }
            int userNum = 0;

            for (int r = 5;r < sheet.getLastRowNum();r++){
                HSSFRow rowr=sheet.getRow(r);
                for (int c = 1;c < rowr.getLastCellNum();c++){
                    if (!isMergedRegion(sheet, r, c)){
                        if (userNum < listUser.size()){
                            rowr.createCell(c).setCellValue(listUser.get(userNum).toString());
                            userNum++;
                        }
                    }
                }
            }

            /*//创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet=wb.createSheet(" 2020年全国教育科研工作会议座次图");


            HSSFCellStyle cellStyle = wb.createCellStyle();  //创建设置EXCEL表格样式对象 cellStyle
            cellStyle.setAlignment(HorizontalAlignment.CENTER);


            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1=sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell=row1.createCell(0);
            //设置单元格内容
            cell.setCellValue(" 2020年全国教育科研工作会议座次图");
            cell.setCellStyle(cellStyle);
            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,18));
            //在sheet里创建第二行
            HSSFRow row2=sheet.createRow(3);
            //创建单元格并设置单元格内容
            int row2num = 3;
            row2.createCell(3).setCellValue("于发友");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,3,4));
            row2.createCell(5).setCellValue("马  涛");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,5,6));
            row2.createCell(7).setCellValue("殷长春");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,7,8));
            row2.createCell(9).setCellValue("田学军");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,9,10));
            row2.createCell(11).setCellValue("崔保师");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,11,12));
            row2.createCell(13).setCellValue("史习琳");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,13,14));
            row2.createCell(15).setCellValue("刘贵华");
            sheet.addMergedRegion(new CellRangeAddress(row2num,row2num,15,16));

            row2.getCell(3).setCellStyle(cellStyle);
            row2.getCell(5).setCellStyle(cellStyle);
            row2.getCell(7).setCellStyle(cellStyle);
            row2.getCell(9).setCellStyle(cellStyle);
            row2.getCell(11).setCellStyle(cellStyle);
            row2.getCell(13).setCellStyle(cellStyle);
            row2.getCell(15).setCellStyle(cellStyle);*/


            //输出Excel文件
            OutputStream output=response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=details.xls");
            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(HSSFSheet sheet, int row, int column) {
        boolean isMerged = false;//判断是否合并单元格

        //获取sheet中有多少个合并单元格
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            // 获取合并后的单元格
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow //判断行
                    && column >= firstColumn && column <= lastColumn) {//判断列
                isMerged = true;
                break;
            }
        }
        return isMerged;
    }
}
