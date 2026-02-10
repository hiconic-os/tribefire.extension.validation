package tribefire.extension.validation.api;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.reflection.EntityType;

public class UmbrellaReasoning<R extends Reason> implements Consumer<Reason> {
	private EntityType<R> type;
	private R reason;
	private Consumer<R> configurer;

	private UmbrellaReasoning(EntityType<R> type, Consumer<R> configurer) {
		this.type = type;
		this.configurer = configurer;
	}
	
	public static <R extends Reason> UmbrellaReasoning<R> create(EntityType<R> type, Consumer<R> configurer) {
		return new UmbrellaReasoning<R>(type, configurer);
	}
	
	public static <R extends Reason> UmbrellaReasoning<R> create(EntityType<R> type, Supplier<String> textSupplier) {
		return new UmbrellaReasoning<R>(type, r -> r.setText(textSupplier.get()));
	}
	
	public static <R extends Reason> UmbrellaReasoning<R> create(EntityType<R> type, String text) {
		return new UmbrellaReasoning<R>(type, r -> r.setText(text));
	}

	@Override
	public void accept(Reason r) {
		if (r == null)
			return;
		
		if (reason == null) {
			reason = type.create();
			configurer.accept(reason);
		}

		reason.getReasons().add(r);
	}
	
	public void forwardIfReasonable(Consumer<Reason> collector) {
		if (reason != null)
			collector.accept(reason);
	}
	
	public R getReason() {
		return reason;
	}
}