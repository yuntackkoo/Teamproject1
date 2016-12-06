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
		this.whetherplain.add("���� : �ʵ� ���� ��� �ϼ��ε��� ���ݷ� +1, ü�� +1");
		this.whetherplain.add("���� : ���� �ڵ� ���� �ϼ��ΰ� ����ī���� ��ȯ/��� �ڽ�Ʈ 2�� ���");
		this.whetherplain.add("��ǳ : �ʵ� ���� ���  �ϼ����� 1������ ������ �̵�");
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