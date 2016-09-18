package br.unicamp.ic.lis.tograph.etl.spreadsheet;

import br.unicamp.ic.lis.ddex.spreadsheet.Cell;
import br.unicamp.ic.lis.ddex.spreadsheet.Column;
import br.unicamp.ic.lis.ddex.spreadsheet.Row;
import br.unicamp.ic.lis.ddex.spreadsheet.Sheet;
import br.unicamp.ic.lis.ddex.spreadsheet.SpreadsheetObject;
import br.unicamp.ic.lis.ddex.spreadsheet.SpreadsheetProperties;
import br.unicamp.ic.lis.ddex.spreadsheet.builder.ISpreadsheetBuilder;
import br.unicamp.ic.lis.tograph.builder.IGraphBuilder;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperties;
import br.unicamp.ic.lis.tograph.graph.GraphElementProperty;
import br.unicamp.ic.lis.tograph.graph.GraphNode;

public class SpreadsheetToGraph implements ISpreadsheetBuilder {

	private String ssFilePath;
	private IGraphBuilder builder;
	private String fileExtension;
	private boolean debubMessage;

	private GraphNode resourceNode;
	private GraphNode resourceRepresentationNode;
	private GraphNode lastRowNode;
	private GraphNode lastCellNode;

	private boolean firstRow = true;
	private boolean firstCell = true;
	private String objectuuid;
	private String objectURI;

	public SpreadsheetToGraph(IGraphBuilder builder, String fileExtension) throws Exception {

		this.builder = builder;
		this.fileExtension = fileExtension;

		this.objectuuid = this.newUUID();

	}

	public String getObjectURI() {
		return objectURI;
	}

	private String newUUID() {
		return java.util.UUID.randomUUID() + "";
	}

	@Override
	public void foundDocumentBegin(SpreadsheetProperties ss) {
		try {
			System.out.println(this.ssFilePath);

			String filename = ss.getFilePath().substring(ss.getFilePath().lastIndexOf('/') + 1,
					ss.getFilePath().length() - 1);

			// Inserting node and properties for the representation of a
			// resource

			this.resourceNode = this.builder.createNode("resource");

			this.objectURI = this.resourceNode.toString();
			this.builder.addProperty(this.resourceNode, new GraphElementProperty("ls_file_path", ss.getFilePath()));
			this.builder.addProperty(this.resourceNode, new GraphElementProperty("ls_file_name", filename));
			this.builder.addProperty(this.resourceNode, new GraphElementProperty("ls_file_extension", fileExtension));
			this.builder.addProperty(this.resourceNode,
					new GraphElementProperty("ls_resource_type", "ls_physical_file"));

			String mimePropertyValue = "Unknow";

			if (this.fileExtension.equals("csv")) {
				
				mimePropertyValue = "txt/csv";
			
			} else if (this.fileExtension.equals("xls")) {

				mimePropertyValue = "application/vnd.ms-excel";
			
			} else if (this.fileExtension.equals("xlsx")) {

				mimePropertyValue = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			}

			this.builder.addProperty(this.resourceNode, new GraphElementProperty("ls_mime", mimePropertyValue));

			// Inserting node and properties for the representation of the
			// resource
			this.resourceRepresentationNode = this.builder.createNode("resourceRepresentation");

			GraphElementProperties resourceRepresentationProperties = new GraphElementProperties();

			resourceRepresentationProperties.insertProperty("ls_mime", mimePropertyValue);
			resourceRepresentationProperties.insertProperty("ls_physicalID", this.newUUID());
			resourceRepresentationProperties.insertProperty("ls_scaleIndex", "0");
			resourceRepresentationProperties.insertProperty("ls_version", "1");
			resourceRepresentationProperties.insertProperty("ls_physicalID", this.newUUID());
			resourceRepresentationProperties.insertProperty("ls_scaleIndex", "1");
			resourceRepresentationProperties.insertProperty("ls_version", "1.1");
			resourceRepresentationProperties.insertProperty("ls_associatedRID", this.objectuuid);

			this.builder.setProperties(this.resourceRepresentationNode, resourceRepresentationProperties);

			this.builder.createRelation(this.resourceRepresentationNode, "ls_rel_represents", this.resourceNode);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void foundColumn(Column arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void foundCell(Cell cell) {

		try {

			if (!this.firstCell) {
				GraphNode tempCellNode = this.builder.createNode("data_block");
				this.builder.createRelation(this.lastCellNode, "ls_has_next_block", tempCellNode);
				this.lastCellNode = tempCellNode;

			} else {
				this.lastCellNode = this.builder.createNode("data_block");
				this.builder.createRelation(this.lastRowNode, "ls_contains", this.lastCellNode);
				this.firstCell = false;
			}

			this.builder.setProperty(this.lastCellNode, new GraphElementProperty("ls_block_type", "ss_cell"));
			this.builder.addProperty(this.lastCellNode, new GraphElementProperty("ls_physicalID", this.newUUID()));
			this.builder.addProperty(this.lastCellNode, new GraphElementProperty("ls_scaleIndex", "1"));
			this.builder.addProperty(this.lastCellNode, new GraphElementProperty("ls_version", "1"));
			this.builder.addProperty(this.lastCellNode, new GraphElementProperty("ls_content", cell.getContent()));
			this.builder.addProperty(this.resourceRepresentationNode,
					new GraphElementProperty("ls_associatedRID", this.objectuuid));

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void foundDocumentEnd(SpreadsheetProperties arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void foundObject(SpreadsheetObject arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void foundRow(Row row) {
		try {
			System.out.println("Row " + row.getIndex() + "... ");
			this.firstCell = true;

			if (!this.firstRow) {
				GraphNode tempRowNode = this.builder.createNode("data_block");
				this.builder.createRelation(this.lastRowNode, "ls_has_next_block", tempRowNode);
				this.lastRowNode = tempRowNode;

			} else {
				this.lastRowNode = this.builder.createNode("data_block");
				this.builder.createRelation(this.resourceNode, "ls_has_first_block", this.lastRowNode);
				this.firstRow = false;
			}

			this.builder.addProperty(this.lastRowNode, new GraphElementProperty("ls_block_type", "ss_row"));
			this.builder.addProperty(this.lastRowNode, new GraphElementProperty("ls_physicalID", this.newUUID()));
			this.builder.addProperty(this.lastRowNode, new GraphElementProperty("ls_scaleIndex", "1"));
			this.builder.addProperty(this.lastRowNode, new GraphElementProperty("ls_version", "1"));
			this.builder.addProperty(this.lastRowNode, new GraphElementProperty("ls_parsed", "0"));
			this.builder.addProperty(this.lastRowNode, new GraphElementProperty("ls_label", "trajectory"));
			this.builder.addProperty(this.resourceRepresentationNode,
					new GraphElementProperty("ls_associatedRID", this.objectuuid));

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	@Override
	public void foundSheet(Sheet arg0) {
		// TODO Auto-generated method stub

	}

}
