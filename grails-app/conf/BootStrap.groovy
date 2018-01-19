import grails.util.Environment

import com.prizy.domain.Price
import com.prizy.domain.Product


class BootStrap {

	String cvsSplitBy = ",";
	Product product;
	Price price;
	
    def init = { servletContext ->
		if (Environment.current != Environment.TEST) {
			if(Product.count() == 0) {
				
				/*ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource("products.csv").getFile());
				
				BufferedReader br = new BufferedReader(new FileReader(file))
				List<String> lines = br.readLines()
				lines.forEach{line ->
					String[] pro = line.split(cvsSplitBy);
					product = new Product(barcode: pro[0], productName: pro[1], description: pro[2]);
					product.save()
					
					for(int i=0;i<getRandom(25, 30);i++) {
						price = new Price(price: getRandom(10, 100), product: product)
						price.save()
					}
				}*/
			}
		}
		
	}
    def destroy = {
    }
	
	public static BigDecimal getRandom(int mi, int ma) {
		BigDecimal min = new BigDecimal(mi);
		BigDecimal max = new BigDecimal(ma);
		BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
	    return randomBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
}
