
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;


public class ImageProcess{

	public int[] red;
	public int[] green;
	public int[] blue;
	public int[][] color;
	public double w;
	public double h;

	public ImageProcess() {
	}
	public Image doScale(Image image,int width,int height){
		boolean isNeedScale = false;
		w = image.getWidth(null);
		h = image.getHeight(null);
		if(w>width){
			w = width;
			isNeedScale = true;
		}
		if(h>height){
			h = height;
			isNeedScale = true;
		}
		if(isNeedScale){
			return scaleImage(image,Integer.toString((int)w) + "*" + Integer.toString((int)h));
		}
		return image;
	}
	public Image scaleImage(Image image, String size) {
		changeToBuffImageAndReadColor(image);
		double destW, destH;
		StringTokenizer st = new StringTokenizer(size, "*");
		destW = Integer.parseInt(st.nextToken());
		destH = Integer.parseInt(st.nextToken());
		// 新建image
		BufferedImage mybi = new BufferedImage((int) destW, (int) destH,
				BufferedImage.TYPE_INT_RGB);
		int srcX, srcY, tempR, tempG, tempB, myRGB;
		double floatsrcX, floatsrcY, u, v;

		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				red[(int) (row * w + col)] = (int) ((color[row][col] & 0xff0000) >> 16);
				green[(int) (row * w + col)] = (int) ((color[row][col] & 0xff00) >> 8);
				blue[(int) (row * w + col)] = (int) ((color[row][col] & 0xff));
			}
		}

		// 双线性插值
		for (int row = 0; row < destH - 2; row++) {
			for (int col = 0; col < destW - 2; col++) {
				// 原图的坐标
				floatsrcX = row * (h / destH);
				floatsrcY = col * (w / destW);
				srcX = (int) floatsrcX;
				srcY = (int) floatsrcY;
				u = floatsrcX - srcX;
				v = floatsrcY - srcY;
				tempR = (int) ((1 - u) * (1 - v) * red[(int) (w * srcX + srcY)]
						+ (1 - u) * v * red[(int) (w * srcX + srcY + 1)] + u
						* (1 - v) * red[(int) (w * (srcX + 1) + srcY)] + u * v
						* red[(int) (w * (srcX + 1) + srcY + 1)]);
				tempG = (int) ((1 - u) * (1 - v)
						* green[(int) (w * srcX + srcY)] + (1 - u) * v
						* green[(int) (w * srcX + srcY + 1)] + u * (1 - v)
						* green[(int) (w * (srcX + 1) + srcY)] + u * v
						* green[(int) (w * (srcX + 1) + srcY + 1)]);
				tempB = (int) ((1 - u) * (1 - v)
						* blue[(int) (w * srcX + srcY)] + (1 - u) * v
						* blue[(int) (w * srcX + srcY + 1)] + u * (1 - v)
						* blue[(int) (w * (srcX + 1) + srcY)] + u * v
						* blue[(int) (w * (srcX + 1) + srcY + 1)]);
				myRGB = ((int) ((tempR & 0xff) << 16))
						| ((int) ((tempG & 0xff) << 8)) | (int) (tempB & 0xff);
				mybi.setRGB(col, row, myRGB);
			}
		}
		// 消灭图像右边的黑框效应
		for (int row = 0; row < destH; row++) {
			for (int col = (int) (destW - 2), srccol = (int) (w - 2); col < destW; col++, srccol++) {
				mybi.setRGB(col, row, color[(int) (row * (h / destH))][srccol]);
			}
		}
		// 消灭图像下边的黑框效应
		for (int row = (int) (destH - 2), srcrow = (int) (h - 2); row < destH; row++, srcrow++) {
			for (int col = 0; col < destW; col++) {
				mybi.setRGB(col, row, color[srcrow][(int) (col * (w / destW))]);
			}
		}
		return mybi;
	}

	private BufferedImage changeToBuffImageAndReadColor(Image image) {
		w = image.getWidth(null);
		h = image.getHeight(null);
		BufferedImage bi = new BufferedImage((int) w, (int) h,
				BufferedImage.TYPE_INT_RGB);
		Graphics gh = bi.getGraphics();
		gh.drawImage(image, 0, 0, null);
		color = new int[(int) h][(int) w];
		red = new int[(int) (h * w)];
		green = new int[(int) (h * w)];
		blue = new int[(int) (h * w)];
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				color[row][col] = bi.getRGB(col, row);
			}
		}
		return bi;
	}

}
