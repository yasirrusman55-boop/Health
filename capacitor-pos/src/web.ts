import { WebPlugin } from '@capacitor/core';

import type { CapacitorPosPlugin } from './definitions';

export class CapacitorPosWeb extends WebPlugin implements CapacitorPosPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
