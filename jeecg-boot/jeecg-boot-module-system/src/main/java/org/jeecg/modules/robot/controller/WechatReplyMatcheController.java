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
import org.jeecg.modules.robot.entity.WechatReplyMatche;
import org.jeecg.modules.robot.service.IWechatReplyMatcheService;
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
 * @Description: 回复匹配
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="回复匹配")
@RestController
@RequestMapping("/robot/wechatReplyMatche")
public class WechatReplyMatcheController extends JeecgController<WechatReplyMatche, IWechatReplyMatcheService> {
	@Autowired
	private IWechatReplyMatcheService wechatReplyMatcheService;
	
	/**
	 * 分页列表查询
	 *
	 * @param wechatReplyMatche
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "回复匹配-分页列表查询")
	@ApiOperation(value="回复匹配-分页列表查询", notes="回复匹配-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WechatReplyMatche wechatReplyMatche,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WechatReplyMatche> queryWrapper = QueryGenerator.initQueryWrapper(wechatReplyMatche, req.getParameterMap());
		Page<WechatReplyMatche> page = new Page<WechatReplyMatche>(pageNo, pageSize);
		IPage<WechatReplyMatche> pageList = wechatReplyMatcheService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param wechatReplyMatche
	 * @return
	 */
	@AutoLog(value = "回复匹配-添加")
	@ApiOperation(value="回复匹配-添加", notes="回复匹配-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WechatReplyMatche wechatReplyMatche) {
		wechatReplyMatcheService.save(wechatReplyMatche);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param wechatReplyMatche
	 * @return
	 */
	@AutoLog(value = "回复匹配-编辑")
	@ApiOperation(value="回复匹配-编辑", notes="回复匹配-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WechatReplyMatche wechatReplyMatche) {
		wechatReplyMatcheService.updateById(wechatReplyMatche);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "回复匹配-通过id删除")
	@ApiOperation(value="回复匹配-通过id删除", notes="回复匹配-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wechatReplyMatcheService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "回复匹配-批量删除")
	@ApiOperation(value="回复匹配-批量删除", notes="回复匹配-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wechatReplyMatcheService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "回复匹配-通过id查询")
	@ApiOperation(value="回复匹配-通过id查询", notes="回复匹配-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WechatReplyMatche wechatReplyMatche = wechatReplyMatcheService.getById(id);
		return Result.ok(wechatReplyMatche);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param wechatReplyMatche
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WechatReplyMatche wechatReplyMatche) {
      return super.exportXls(request, wechatReplyMatche, WechatReplyMatche.class, "回复匹配");
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
      return super.importExcel(request, response, WechatReplyMatche.class);
  }

}
