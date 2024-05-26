
package acme.features.any.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class AnyBannerAdvisor {

	@Autowired
	protected AnyBannerRepository repository;


	@ModelAttribute("banner")
	public Banner getBanner() {
		Banner result;

		try {
			result = this.repository.findRandomBanner();
		} catch (final Throwable oops) {
			result = null;
		}
		return result;
	}
}
