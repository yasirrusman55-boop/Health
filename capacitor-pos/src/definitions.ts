export interface CapacitorPosPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
