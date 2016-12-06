package player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import dataload.LoadData;

public class whetherPanel extends JPanel
{
	LoadData data = LoadData.getInstance();
	private int whether;
	private int nextturn;
	BufferedImage image;
	List<String> whetherplain = new ArrayList<String>();
	
	public whetherPanel()
	{
		this.whetherplain.add("맑음 : 필드 내의 모든 하수인들의 공격력 +1, 체력 +1");
		this.whetherplain.add("폭우 : 현재 핸드 내의 하수인과 마법카드의 소환/사용 코스트 2배 상승");
		this.whetherplain.add("폭풍 : 필드 내의 모든  하수인중 1마리를 묘지로 이동");
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(),this.getHeight(),null);
	}

	public void setWhether(int whether)
	{
		this.whether = whether;
		image = data.getWhetheimage(this.whether);
		this.setToolTipText(this.whetherplain.get(this.whether));
	}

	public void setNextturn(int nextturn)
	{
		this.nextturn = nextturn;
	}
	
}