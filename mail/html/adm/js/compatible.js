//���´�����Ϊ�˽��firefox��ie��event�¼��ļ��������⣬firefoxû��event�¼�
		//����鿪ʼ
		if(window.addEventListener)
		{
			FixPrototypeForGecko();
		}
		function FixPrototypeForGecko()
		{
			HTMLElement.prototype.__defineGetter__("runtimeStyle",element_prototype_get_runtimeStyle);
			window.constructor.prototype.__defineGetter__("event",window_prototype_get_event);
			Event.prototype.__defineGetter__("srcElement",event_prototype_get_srcElement);
		}
		function element_prototype_get_runtimeStyle()
		{
			//return style instead...
			return this.style;
		}
		function window_prototype_get_event()
		{
			return SearchEvent();
		}
		function event_prototype_get_srcElement()
		{
			return this.target;
		}
		
		function SearchEvent()
		{
			//IE
			if(document.all)
				return window.event;
				
			func=SearchEvent.caller;
			while(func!=null)
			{
				var arg0=func.arguments[0];
				if(arg0)
				{
					if(arg0.constructor==Event)
						return arg0;
				}
				func=func.caller;
			}
			return null;
		}
		//��������