package cn.stylefeng.guns.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payPage")
public class payPageController {
	@RequestMapping("")
	public String payPage() {
		return "/pay/pay.html";
	}
}
