package org.jeecg.modules.robot.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.robot.entity.WechatRobot;
import org.jeecg.modules.robot.service.IWechatRobotService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 机器人
 * @Author: jeecg-boot
 * @Date:   2020-08-21
 * @Version: V1.0
 */
@Slf4j
@Api(tags="机器人")
@RestController
@RequestMapping("/robot/wechatRobot")
public class WechatRobotController extends JeecgController<WechatRobot, IWechatRobotService> {
	@Autowired
	private IWechatRobotService wechatRobotService;
	
	/**
	 * 分页列表查询
	 *
	 * @param wechatRobot
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "机器人-分页列表查询")
	@ApiOperation(value="机器人-分页列表查询", notes="机器人-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WechatRobot wechatRobot,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WechatRobot> queryWrapper = QueryGenerator.initQueryWrapper(wechatRobot, req.getParameterMap());
		Page<WechatRobot> page = new Page<WechatRobot>(pageNo, pageSize);
		IPage<WechatRobot> pageList = wechatRobotService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param wechatRobot
	 * @return
	 */
	@AutoLog(value = "机器人-添加")
	@ApiOperation(value="机器人-添加", notes="机器人-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WechatRobot wechatRobot) {
		wechatRobotService.save(wechatRobot);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param wechatRobot
	 * @return
	 */
	@AutoLog(value = "机器人-编辑")
	@ApiOperation(value="机器人-编辑", notes="机器人-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WechatRobot wechatRobot) {
		wechatRobotService.updateById(wechatRobot);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "机器人-通过id删除")
	@ApiOperation(value="机器人-通过id删除", notes="机器人-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wechatRobotService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "机器人-批量删除")
	@ApiOperation(value="机器人-批量删除", notes="机器人-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wechatRobotService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "机器人-通过id查询")
	@ApiOperation(value="机器人-通过id查询", notes="机器人-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WechatRobot wechatRobot = wechatRobotService.getById(id);
		return Result.ok(wechatRobot);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param wechatRobot
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WechatRobot wechatRobot) {
      return super.exportXls(request, wechatRobot, WechatRobot.class, "机器人");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, WechatRobot.class);
  }

}
