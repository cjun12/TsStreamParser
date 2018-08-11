package section;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cjun.stream.packet.TsPacket;

public class SectionAssembler {
	
	private ByteArrayOutputStream out;
	private int length;
	
	public void accept(TsPacket packet){
		if(packet.getHeader().getPayload_unit_start_indicator()==0x01){
			if(out == null){
				out = new ByteArrayOutputStream(1024);
			}
			out.reset();
			try {
				out.write(packet.getBody());
				SectionHeader header = new SectionHeader(packet.getBody());
				length = header.getSection_length();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			
		}
	}
	
	private void assemble(){
		
	}
}
