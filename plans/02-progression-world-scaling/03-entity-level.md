# World Scaling Plan — Entity Level

**Goal:** atribuir nível a entidades no ponto correto do lifecycle e preservar o resultado sem reaplicação.

- [ ] Definir momento canônico de cálculo no spawn/load.
- [ ] Combinar area level e relevant player level conforme fórmula definida.
- [ ] Persistir metadados necessários na entidade.
- [ ] Impedir scaling duplicado após chunk unload/reload.
- [ ] Definir fallback para mobs externos e entidades sem categoria conhecida.

**Acceptance:** a entidade recebe um nível estável uma vez e mantém atributos corretos após save/load.