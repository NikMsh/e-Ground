export interface Offer {
  id: string;
  name: string;
  category: string;
  price: number;
  description: string;
}

export const defaultOffer: Offer = {
  id: null,
  name: '',
  category: '',
  price: 0,
  description: ''
};
