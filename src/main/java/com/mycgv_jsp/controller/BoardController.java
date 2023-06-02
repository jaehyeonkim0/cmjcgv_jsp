package com.mycgv_jsp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.service.BoardService;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PageServiceImpl pageServiceImpl;
	
	 /** board_list.do - �Խñ� ��ü ����Ʈ  */
	@RequestMapping(value="/board_list.do", method=RequestMethod.GET)
	public ModelAndView board_list(String page) {
		ModelAndView model = new ModelAndView();		
		Map<String, Integer> param = pageServiceImpl.getPageResult(page, "board");
		
		ArrayList<BoardVo> list = boardService.getSelect(param.get("startCount"), param.get("endCount"));
	
		model.addObject("list", list);
		model.addObject("totals", param.get("totals")); //
		model.addObject("pageSize", param.get("pageSize"));
		model.addObject("maxSize", param.get("maxSize"));
		model.addObject("page", param.get("page"));
		
		model.setViewName("/board/board_list");
		
		return model;
	}
	
	
	//�Խñ� Ŭ�� �� ���̱�
	@RequestMapping(value="/board_content.do", method=RequestMethod.GET)
	public ModelAndView board_content(String bid) {
		ModelAndView mav = new ModelAndView();
		
		//BoardDao boardDao = new BoardDao();
		BoardVo boardVo = boardService.getSelect(bid);
		
		mav.addObject("boardVo", boardVo);
		mav.setViewName("/board/board_content");
		
		//�󼼺���� ���� ��ȸ�� ����(DB)
		if(boardVo!=null) {
			//boardVo�� null�� �ƴϸ�=������ ������
			boardService.getUpdateHits(bid); //Ŭ���� bid�� �۸� ��ȸ�� ����
		}
		return mav;
	}
	
	@RequestMapping(value="/board_write.do", method=RequestMethod.GET)
	public String board_write() {
		
		return "/board/board_write";
	}
	
	@RequestMapping(value="/board_write_proc.do", method=RequestMethod.POST)
	public String board_write_proc(BoardVo boardVo, HttpServletRequest request) throws Exception {
		
		//1. �۾��� ������ �Ѿ���� �����͸� BoardVo�� ����
		//2. BoardVo �����͸� Dao�� ����
		//3. mycgv_board ���̺� insert
		String viewName="";
		//BoardDao boardDao = new BoardDao();
		
		//���� ���ε�
		//bfile, bsfile ���ϸ� ����
		//������ �ִ��� ���������� Ȯ��; file1�� ������Ÿ���� CommonsMultipartFile�̹Ƿ� �ִ��� Ȯ���ϴ� �޼ҵ� = getOriginalFilename
		//������ ���� ��ġ(resources/upload) ���ε��� ��ġ
		String root_path = request.getSession().getServletContext().getRealPath("/"); //root_path = webapp ��α��� ��
		String attach_path = "\\resources\\upload\\";
		
		if(boardVo.getFile1().getOriginalFilename() != null
			&& !boardVo.getFile1().getOriginalFilename().equals("")) { //������ �����ϸ�
			
			//BSFILE ���� �ߺ� ó��
			UUID uuid = UUID.randomUUID();
			String bfile = boardVo.getFile1().getOriginalFilename();
			String bsfile = uuid + "_" + bfile;
			
			System.out.println(root_path);
			System.out.println(attach_path);
			System.out.println(bfile);
			System.out.println(bsfile);
			
			boardVo.setBfile(bfile);
			boardVo.setBsfile(bsfile);
		}else {
			System.out.println("���� ����");
		}
		
		int result = boardService.getInsert(boardVo);
		
		if(result==1) {
			//������ �����ϸ� ������ ����
			File saveFile = new File(root_path+attach_path+boardVo.getBsfile());
			boardVo.getFile1().transferTo(saveFile);
			System.out.println(saveFile);
			viewName = "redirect:/board_list.do";
		}else {
			System.out.println("���� ����");
		}
			
		
		return viewName;
	}
	
	@RequestMapping(value="/board_update.do", method=RequestMethod.GET)
	public ModelAndView board_update(String bid) {
		//�������� �󼼺��� ������ �����ͼ� ���� �߰��Ͽ� ���
		ModelAndView mav = new ModelAndView();
		//BoardDao boardDao = new BoardDao();
		BoardVo boardVo = boardService.getSelect(bid);
		
		mav.addObject("boardVo", boardVo);
		mav.setViewName("/board/board_update");
		
		return mav;
	}
	
	@RequestMapping(value="/board_update_proc.do", method=RequestMethod.POST)
	public String board_update_proc(BoardVo boardVo) {
		String viewName = "";
		//BoardDao boardDao = new BoardDao();
		int result = boardService.getUpdate(boardVo);
		
		if(result==1)
			viewName="redirect:/board_list.do";
		
		return viewName;
		
	}
	
	@RequestMapping(value="/board_delete.do", method=RequestMethod.GET)
	public ModelAndView board_delete(String bid) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("bid", bid);
		mav.setViewName("/board/board_delete");
		
		return mav;
	}
	
	@RequestMapping(value="/board_delete_proc.do", method=RequestMethod.POST)
	public String board_delete_proc(String bid) {
		
		String viewName = "";
		//BoardDao boardDao = new BoardDao();
		int result = boardService.getDelete(bid);
		
		if(result==1)
			viewName = "redirect:/board_list.do";
		
		return viewName;
		
	}
	
	//header �Խ���(JSON) ȣ��Ǵ� �ּ�
	@RequestMapping(value="/board_list_json.do", method=RequestMethod.GET)
	public String board_list_json() {
		return "/board/board_list_json";
	}
	
	/** board_list_json_data.do - ajax���� ȣ��Ǵ� �Խñ� ��ü ����Ʈ(JSON)  */
	@RequestMapping(value="/board_list_json_data.do", method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String board_list_json_data(String page) {
		
		BoardDao boardDao = new BoardDao();
		Map<String, Integer> param = pageServiceImpl.getPageResult(page, "board");
		
		ArrayList<BoardVo> list = boardService.getSelect(param.get("startCount"), param.get("endCount"));
		//list ��ü�� �����͸� JSON ���·� ����
		JsonObject jlist = new JsonObject(); //jlist = list
		JsonArray jarray = new JsonArray();
		
		for(BoardVo boardVo : list) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("rno", boardVo.getRno()); //{rno:1}
			jobj.addProperty("btitle", boardVo.getBtitle()); //{rno:1, btitle: "~~"}
			jobj.addProperty("bhits", boardVo.getBhits());
			jobj.addProperty("id", boardVo.getId());
			jobj.addProperty("bdate", boardVo.getBdate());
			
			jarray.add(jobj);
		}
		jlist.add("jlist", jarray);
		jlist.addProperty("totals", param.get("totals"));
		jlist.addProperty("pageSize", param.get("pageSize"));
		jlist.addProperty("maxSize", param.get("maxSize"));
		jlist.addProperty("page", param.get("page"));
		
		//jdata = {jlist:[{~~}], "totals":10, "pageSize": 2, } �̷��� �ڿ� �߰��ϴ°� 227~230����
		/*
		{list:[{rno:1, btitle:"�Խñ�1", bhits:1, id:"hong1234", bdate:"2023-05-20"},
		       {rno:2, btitle:"�Խñ�1", bhits:1, id:"hong1234", bdate:"2023-05-20"},
		       {rno:3, btitle:"�Խñ�1", bhits:1, id:"hong1234", bdate:"2023-05-20"},
		       {rno:4, btitle:"�Խñ�1", bhits:1, id:"hong1234", bdate:"2023-05-20"},
		       {rno:5, btitle:"�Խñ�1", bhits:1, id:"hong1234", bdate:"2023-05-20"}
			]
		}
		
		JsonObject jobj = new JsonObject();
		jobj.addProperty("rno", boardVo.getRno());
		={rno:1}
		*/
		
		/*
		model.addObject("list", list);
		model.addObject("totals", dbCount); //
		model.addObject("pageSize", pageSize);
		model.addObject("maxSize", pageCount);
		model.addObject("page", reqPage);
		
		model.setViewName("/board/board_list");
		*/
		return new Gson().toJson(jlist);//json�� �ƴ϶� ���� string�̴�
		
	}
	
	
}

























