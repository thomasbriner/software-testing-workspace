package ch.testing.pricing.service.workflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.service.OfferService;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItemImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.pricing.service.workflow.PricingContext;
import org.broadleafcommerce.core.workflow.BaseActivity;

import ch.testing.order.service.workflow.add.ValidateHeatRangeRestrictionActivity;
import ch.testing.util.domain.TestingDateTimeProvider;

public class WeekendDiscountPricingActivity extends
		BaseActivity<PricingContext> {

	private static final Log LOG = LogFactory
			.getLog(WeekendDiscountPricingActivity.class);
	private SimpleDateFormat formatter = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm:ss");

	@Resource(name = "blOfferService")
	private OfferService offerService;

	@Override
	public PricingContext execute(PricingContext context) throws Exception {

		WeekendDiscountValidator validator = new WeekendDiscountValidator();
		validator.initializeWithWeekendNumber(4);
		Date testingDate = TestingDateTimeProvider.getInstance()
				.getTestingDate();
		LOG.info("Testing Date is " + formatter.format(testingDate));

		if (validator.isAuthorizedForDiscount(testingDate)) {
			LOG.error("is authorized for WeekendDiscountPricing");

			Order order = context.getSeedData();
			
			List<Offer> offers = offerService.findAllOffers();
			List<Offer> weekendDiscountOfferList = new ArrayList<Offer>();
			for (Offer offer : offers) {
				if (offer.getName().equals("WeekendDiscount")) {
					weekendDiscountOfferList.add(offer);
				}
			}
			offerService.applyOffersToOrder(weekendDiscountOfferList, order);
			
		}

		return context;
	}

}

