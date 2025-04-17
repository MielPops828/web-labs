export interface Task{
    id: number;
    name: string;
    description: string;
    plannedEndDate: Date;
    completed: boolean;
    projectId: number;
}