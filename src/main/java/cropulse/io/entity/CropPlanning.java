package cropulse.io.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crop_planning")

public class CropPlanning {

    @Id
    private String id;

    private String plot;
    private String cropType;
    private Date sowingDate;
    private String yield;
    private String seedsUsed;
    private String revenue;
    private String cultivation;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	public Date getSowingDate() {
		return sowingDate;
	}
	public void setSowingDate(Date sowingDate) {
		this.sowingDate = sowingDate;
	}
	public String getYield() {
		return yield;
	}
	public void setYield(String yield) {
		this.yield = yield;
	}
	public String getSeedsUsed() {
		return seedsUsed;
	}
	public void setSeedsUsed(String seedsUsed) {
		this.seedsUsed = seedsUsed;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public String getCultivation() {
		return cultivation;
	}
	public void setCultivation(String cultivation) {
		this.cultivation = cultivation;
	}

    
}
