export type Filter = {
  criteria: string;
  operator: string;
  value: string;
};

export type FilterCollection = {
  id: number
  name: string;
  filterList: Filter[];
};
