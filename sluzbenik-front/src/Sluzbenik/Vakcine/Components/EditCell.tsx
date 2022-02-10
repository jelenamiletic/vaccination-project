import { Input } from "reactstrap";
import { Cell } from "rsuite-table";

export const EditCell = ({
	rowData,
	dataKey,
	onChange,
	editMode,
	...props
}: any) => {
	const editing = rowData.status === "IZMENI";
	return (
		<Cell {...props} className={editing ? "table-content-editing" : ""}>
			{editing ? (
				<Input
					style={{ textAlign: "center", height: "35px" }}
					defaultValue={rowData[dataKey]}
					onChange={(event) => {
						onChange && onChange(rowData["va:Naziv"], event.target.value);
					}}
				/>
			) : (
				<span className="table-content-edit-span">{rowData[dataKey]}</span>
			)}
		</Cell>
	);
};
