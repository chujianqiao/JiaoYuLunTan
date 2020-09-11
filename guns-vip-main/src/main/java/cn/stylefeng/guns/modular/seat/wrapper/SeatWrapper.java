package cn.stylefeng.guns.modular.seat.wrapper;

import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.mapper.MeetMapper;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class SeatWrapper extends BaseControllerWrapper {

	private MeetMapper meetMapper = SpringContextHolder.getBean(MeetMapper.class);

	public SeatWrapper(Map<String, Object> single) {
		super(single);
	}

	public SeatWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public SeatWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public SeatWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		//会议类型
		Long meetType = Long.parseLong(map.get("meetType").toString());
		if(meetType.equals(1L)){
			Long meetId = Long.parseLong(map.get("meetId").toString());
			Meet meet = this.meetMapper.selectById(meetId);
			String meetName = meet.getMeetName();
			map.put("meetName",meetName);
			map.put("meetTypeStr","大会");
		}else if(meetType.equals(2L)){
			map.put("meetTypeStr","分论坛");
		}
		//排列方式
		String sortType = map.get("seatType").toString();
		if(sortType.equals("A")){
			map.put("seatTypeStr","正常排列（按照从左到右顺序排列）");
		}else if(sortType.equals("B")){
			map.put("seatTypeStr","非正常排列（按照奇偶数排列）");
		}

	}
}
