import { registerPlugin } from '@capacitor/core';

import type { CapacitorPosPlugin } from './definitions';

const CapacitorPos = registerPlugin<CapacitorPosPlugin>('CapacitorPos', {
  web: () => import('./web').then((m) => new m.CapacitorPosWeb()),
});

export * from './definitions';
export { CapacitorPos };
