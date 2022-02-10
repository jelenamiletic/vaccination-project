import { Button } from "reactstrap";
import { Cell } from "rsuite-table";

export const ActionCell = ({ rowData, dataKey, onClick, ...props }: any) => {
	return (
		<Cell {...props} style={{ padding: "6px" }}>
			<Button
				style={{ padding: "5px" }}
				color="primary"
				onClick={() => {
					onClick && onClick(rowData[dataKey]);
				}}
			>
				{rowData.status === "IZMENI" ? "Sacuvaj" : "Izmeni"}
			</Button>
		</Cell>
	);
};
