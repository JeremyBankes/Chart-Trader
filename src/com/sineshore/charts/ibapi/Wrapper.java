package com.sineshore.charts.ibapi;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ib.client.Bar;
import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDescription;
import com.ib.client.ContractDetails;
import com.ib.client.DeltaNeutralContract;
import com.ib.client.DepthMktDataDescription;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.FamilyCode;
import com.ib.client.HistogramEntry;
import com.ib.client.HistoricalTick;
import com.ib.client.HistoricalTickBidAsk;
import com.ib.client.HistoricalTickLast;
import com.ib.client.NewsProvider;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.PriceIncrement;
import com.ib.client.SoftDollarTier;
import com.ib.client.TickAttr;

public interface Wrapper extends EWrapper {

	@Override
	public default void tickPrice(int id, int field, double price, TickAttr attrib) {
		System.out.println("tickPrice");
	}

	@Override
	public default void tickSize(int id, int field, int size) {
		System.out.println("tickSize");
	}

	@Override
	public default void tickOptionComputation(int id, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma,
			double vega, double theta, double undPrice) {
		System.out.println("tickOptionComputation");
	}

	@Override
	public default void tickGeneric(int id, int tickType, double value) {
		System.out.println("tickGeneric");
	}

	@Override
	public default void tickString(int id, int tickType, String value) {
		System.out.println("tickString");
	}

	@Override
	public default void tickEFP(int id, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureLastTradeDate, double dividendImpact, double dividendsToLastTradeDate) {
		System.out.println("tickEFP");
	}

	@Override
	public default void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		System.out.println("orderStatus");
	}

	@Override
	public default void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
		System.out.println("openOrder");
	}

	@Override
	public default void openOrderEnd() {
		System.out.println("openOrderEnd");
	}

	@Override
	public default void updateAccountValue(String key, String value, String currency, String accountName) {
		System.out.println("updateAccountValue");
	}

	@Override
	public default void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		System.out.println("updatePortfolio");
	}

	@Override
	public default void updateAccountTime(String timeStamp) {
		System.out.println("updateAccountTime");
	}

	@Override
	public default void accountDownloadEnd(String accountName) {
		System.out.println("accountDownloadEnd");
	}

	@Override
	public default void nextValidId(int orderId) {
		System.out.println("nextValidId");
	}

	@Override
	public default void contractDetails(int reqId, ContractDetails contractDetails) {
		System.out.println("contractDetails");
	}

	@Override
	public default void bondContractDetails(int reqId, ContractDetails contractDetails) {
		System.out.println("bondContractDetails");
	}

	@Override
	public default void contractDetailsEnd(int reqId) {
		System.out.println("contractDetailsEnd");
	}

	@Override
	public default void execDetails(int reqId, Contract contract, Execution execution) {
		System.out.println("execDetails");
	}

	@Override
	public default void execDetailsEnd(int reqId) {
		System.out.println("execDetailsEnd");
	}

	@Override
	public default void updateMktDepth(int id, int position, int operation, int side, double price, int size) {
		System.out.println("updateMktDepth");
	}

	@Override
	public default void updateMktDepthL2(int id, int position, String marketMaker, int operation, int side, double price, int size) {
		System.out.println("updateMktDepthL2");
	}

	@Override
	public default void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
		System.out.println("updateNewsBulletin");
	}

	@Override
	public default void managedAccounts(String accountsList) {
		System.out.println("managedAccounts");
	}

	@Override
	public default void receiveFA(int faDataType, String xml) {
		System.out.println("receiveFA");
	}

	@Override
	public default void historicalData(int reqId, Bar bar) {
		System.out.println("historicalData");
	}

	@Override
	public default void scannerParameters(String xml) {
		System.out.println("scannerParameters");
	}

	@Override
	public default void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection,
			String legsStr) {
		System.out.println("scannerData");
	}

	@Override
	public default void scannerDataEnd(int reqId) {
		System.out.println("scannerDataEnd");
	}

	@Override
	public default void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
		System.out.println("realtimeBar");
	}

	@Override
	public default void currentTime(long time) {
		System.out.println("currentTime");
	}

	@Override
	public default void fundamentalData(int reqId, String data) {
		System.out.println("fundamentalData");
	}

	@Override
	public default void deltaNeutralValidation(int reqId, DeltaNeutralContract deltaNeutralContract) {
		System.out.println("deltaNeutralValidation");
	}

	@Override
	public default void tickSnapshotEnd(int reqId) {
		System.out.println("tickSnapshotEnd");
	}

	@Override
	public default void marketDataType(int reqId, int marketDataType) {
		System.out.println("marketDataType");
	}

	@Override
	public default void commissionReport(CommissionReport commissionReport) {
		System.out.println("commissionReport");
	}

	@Override
	public default void position(String account, Contract contract, double pos, double avgCost) {
		System.out.println("position");
	}

	@Override
	public default void positionEnd() {
		System.out.println("positionEnd");
	}

	@Override
	public default void accountSummary(int reqId, String account, String tag, String value, String currency) {
		System.out.println("accountSummary");
	}

	@Override
	public default void accountSummaryEnd(int reqId) {
		System.out.println("accountSummaryEnd");
	}

	@Override
	public default void verifyMessageAPI(String apiData) {
		System.out.println("verifyMessageAPI");
	}

	@Override
	public default void verifyCompleted(boolean isSuccessful, String errorText) {
		System.out.println("verifyCompleted");
	}

	@Override
	public default void verifyAndAuthMessageAPI(String apiData, String xyzChallenge) {
		System.out.println("verifyAndAuthMessageAPI");
	}

	@Override
	public default void verifyAndAuthCompleted(boolean isSuccessful, String errorText) {
		System.out.println("verifyAndAuthCompleted");
	}

	@Override
	public default void displayGroupList(int reqId, String groups) {
		System.out.println("displayGroupList");
	}

	@Override
	public default void displayGroupUpdated(int reqId, String contractInfo) {
		System.out.println("displayGroupUpdated");
	}

	@Override
	public default void error(Exception e) {
		System.out.println("error");
	}

	@Override
	public default void error(String str) {
		System.out.println("error");
	}

	@Override
	public default void error(int id, int errorCode, String errorMsg) {
		System.out.println("error");
	}

	@Override
	public default void connectionClosed() {
		System.out.println("connectionClosed");
	}

	@Override
	public default void connectAck() {
		System.out.println("connectAck");
	}

	@Override
	public default void positionMulti(int reqId, String account, String modelCode, Contract contract, double pos, double avgCost) {
		System.out.println("positionMulti");
	}

	@Override
	public default void positionMultiEnd(int reqId) {
		System.out.println("positionMultiEnd");
	}

	@Override
	public default void accountUpdateMulti(int reqId, String account, String modelCode, String key, String value, String currency) {
		System.out.println("accountUpdateMulti");
	}

	@Override
	public default void accountUpdateMultiEnd(int reqId) {
		System.out.println("accountUpdateMultiEnd");
	}

	@Override
	public default void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId, String tradingClass, String multiplier,
			Set<String> expirations, Set<Double> strikes) {
		System.out.println("securityDefinitionOptionalParameter");
	}

	@Override
	public default void securityDefinitionOptionalParameterEnd(int reqId) {
		System.out.println("securityDefinitionOptionalParameterEnd");
	}

	@Override
	public default void softDollarTiers(int reqId, SoftDollarTier[] tiers) {
		System.out.println("softDollarTiers");
	}

	@Override
	public default void familyCodes(FamilyCode[] familyCodes) {
		System.out.println("familyCodes");
	}

	@Override
	public default void symbolSamples(int reqId, ContractDescription[] contractDescriptions) {
		System.out.println("symbolSamples");
	}

	@Override
	public default void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {
		System.out.println("historicalDataEnd");
	}

	@Override
	public default void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {
		System.out.println("mktDepthExchanges");
	}

	@Override
	public default void tickNews(int id, long timeStamp, String providerCode, String articleId, String headline, String extraData) {
		System.out.println("tickNews");
	}

	@Override
	public default void smartComponents(int reqId, Map<Integer, Entry<String, Character>> theMap) {
		System.out.println("smartComponents");
	}

	@Override
	public default void tickReqParams(int id, double minTick, String bboExchange, int snapshotPermissions) {
		System.out.println("tickReqParams");
	}

	@Override
	public default void newsProviders(NewsProvider[] newsProviders) {
		System.out.println("newsProviders");
	}

	@Override
	public default void newsArticle(int requestId, int articleType, String articleText) {
		System.out.println("newsArticle");
	}

	@Override
	public default void historicalNews(int requestId, String time, String providerCode, String articleId, String headline) {
		System.out.println("historicalNews");
	}

	@Override
	public default void historicalNewsEnd(int requestId, boolean hasMore) {
		System.out.println("historicalNewsEnd");
	}

	@Override
	public default void headTimestamp(int reqId, String headTimestamp) {
		System.out.println("headTimestamp");
	}

	@Override
	public default void histogramData(int reqId, List<HistogramEntry> items) {
		System.out.println("histogramData");
	}

	@Override
	public default void historicalDataUpdate(int reqId, Bar bar) {
		System.out.println("historicalDataUpdate");
	}

	@Override
	public default void rerouteMktDataReq(int reqId, int conId, String exchange) {
		System.out.println("rerouteMktDataReq");
	}

	@Override
	public default void rerouteMktDepthReq(int reqId, int conId, String exchange) {
		System.out.println("rerouteMktDepthReq");
	}

	@Override
	public default void marketRule(int marketRuleId, PriceIncrement[] priceIncrements) {
		System.out.println("marketRule");
	}

	@Override
	public default void pnl(int reqId, double dailyPnL, double unrealizedPnL, double realizedPnL) {
		System.out.println("pnl");
	}

	@Override
	public default void pnlSingle(int reqId, int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {
		System.out.println("pnlSingle");
	}

	@Override
	public default void historicalTicks(int reqId, List<HistoricalTick> ticks, boolean done) {
		System.out.println("historicalTicks");
	}

	@Override
	public default void historicalTicksBidAsk(int reqId, List<HistoricalTickBidAsk> ticks, boolean done) {
		System.out.println("historicalTicksBidAsk");
	}

	@Override
	public default void historicalTicksLast(int reqId, List<HistoricalTickLast> ticks, boolean done) {
		System.out.println("historicalTicksLast");
	}

	@Override
	public default void tickByTickAllLast(int reqId, int tickType, long time, double price, int size, TickAttr attribs, String exchange,
			String specialConditions) {
		System.out.println("tickByTickAllLast");
	}

	@Override
	public default void tickByTickBidAsk(int reqId, long time, double bidPrice, double askPrice, int bidSize, int askSize, TickAttr attribs) {
		System.out.println("tickByTickBidAsk");
	}

	@Override
	public default void tickByTickMidPoint(int reqId, long time, double midPoint) {
		System.out.println("tickByTickMidPoint");
	}

}
